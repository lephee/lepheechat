package lephee.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;

import com.iflytek.cloud.speech.LexiconListener;
import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.Setting;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import com.iflytek.cloud.speech.UserWords;

public class AudioToText {

	private static final String APPID = "57c97770";

	private static final String USER_WORDS = "{\"userword\":[{\"name\":\"计算机词汇\",\"words\":[\"随机存储器\",\"只读存储器\",\"扩充数据输出\",\"局部总线\",\"压缩光盘\",\"十七寸显示器\"]},{\"name\":\"我的词汇\",\"words\":[\"槐花树老街\",\"王小贰\",\"发炎\",\"公事\"]}]}";

	private static AudioToText mObject;

	private static StringBuffer mResult = new StringBuffer();
	
	private LepheeRecognizerListener recListener;

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		System.out.println("读文件");
		File file = getMscObj().chooseAFile();
		if (file == null) {
			return;
		}
		System.out.println("========================");
		SpeechUtility.createUtility("appid=" + APPID);
		getMscObj().recognize(file);
	}
	
	public File chooseAFile() {
		JFileChooser jfc = new JFileChooser();
//		jfc.setFileSystemView(FileSystemView.getFileSystemView());
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int i = jfc.showOpenDialog(null);
		if (i == JFileChooser.APPROVE_OPTION) {
			return jfc.getSelectedFile();
		} else {
			return null;
		}
	}

	private static AudioToText getMscObj() {
		if (mObject == null)
			mObject = new AudioToText();
		return mObject;
	}

	// *************************************音频流听写*************************************

	/**
	 * 听写
	 */
	private void recognize(File file) {
		if (SpeechRecognizer.getRecognizer() == null) {
			SpeechRecognizer.createRecognizer();
		}
		recListener = new LepheeRecognizerListener(file);
		recognizePcmfileByte(file);
	}

	/**
	 * 自动化测试注意要点 如果直接从音频文件识别，需要模拟真实的音速，防止音频队列的堵塞
	 */
	public void recognizePcmfileByte(File file) {
		// 1、读取音频文件
		FileInputStream fis = null;
		byte[] voiceBuffer = null;
		try {
			fis = new FileInputStream(file);
			voiceBuffer = new byte[fis.available()];
			fis.read(voiceBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fis) {
					fis.close();
					fis = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 2、音频流听写
		if (0 == voiceBuffer.length) {
			mResult.append("==============错误：文件不对啊==========");
		} else {
			SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();
			recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
			recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
			recognizer.setParameter(SpeechConstant.RESULT_TYPE, "plain");
			recognizer.startListening(recListener);
			ArrayList<byte[]> buffers = splitBuffer(voiceBuffer, voiceBuffer.length, 4800);
			for (int i = 0; i < buffers.size(); i++) {
				// 每次写入msc数据4.8K,相当150ms录音数据
				recognizer.writeAudio(buffers.get(i), 0, buffers.get(i).length);
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			recognizer.stopListening();
		}
	}

	/**
	 * 将字节缓冲区按照固定大小进行分割成数组
	 * 
	 * @param buffer
	 *            缓冲区
	 * @param length
	 *            缓冲区大小
	 * @param spsize
	 *            切割块大小
	 * @return
	 */
	public ArrayList<byte[]> splitBuffer(byte[] buffer, int length, int spsize) {
		ArrayList<byte[]> array = new ArrayList<byte[]>();
		if (spsize <= 0 || length <= 0 || buffer == null || buffer.length < length)
			return array;
		int size = 0;
		while (size < length) {
			int left = length - size;
			if (spsize < left) {
				byte[] sdata = new byte[spsize];
				System.arraycopy(buffer, size, sdata, 0, spsize);
				array.add(sdata);
				size += spsize;
			} else {
				byte[] sdata = new byte[left];
				System.arraycopy(buffer, size, sdata, 0, left);
				array.add(sdata);
				size += left;
			}
		}
		return array;
	}

	
	class LepheeRecognizerListener implements RecognizerListener {
		
		private String fileName;
		
		public LepheeRecognizerListener(File file) {
			if (file == null) {
				fileName = "result/errorName";
			} else {
				String name = file.getName();
				if (name.isEmpty()) {
					fileName = "result/errorName";
				} else {
					String[] split = name.split("\\.");
					fileName = "result/" + split[0];
				}
			}
		}

		public void onBeginOfSpeech() {
			DebugLog.Log("*************开始录音*************");
		}

		public void onEndOfSpeech() {
			DebugLog.Log("onEndOfSpeech enter");
		}

		public void onVolumeChanged(int volume) {

		}

		public void onResult(RecognizerResult result, boolean islast) {
			mResult.append(result.getResultString());

			if (islast) {
				System.out.println("结果：\n" + mResult.toString());
				final String resultStr = mResult.toString();
				Thread t = new Thread(new Runnable() {
					public void run() {
						File f = new File(fileName + ".txt");
						if (f.exists()) {
							f = new File(fileName + "_" + System.currentTimeMillis() + ".txt");
							try {
								f.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
								System.out.println("==============错误：创建文件失败============");
							}
						} else {
							try {
								new File("result").mkdirs();
								f.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						FileOutputStream fos = null;
						try {
							fos = new FileOutputStream(f);
							fos.write(resultStr.getBytes());
							System.out.println("==============结果已经保存到" + f.getAbsolutePath());
							JOptionPane.showMessageDialog(null, "成功，结果已经保存到" + f.getAbsolutePath());
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								fos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mResult.delete(0, mResult.length());
			}
		}

		public void onError(SpeechError error) {
			DebugLog.Log("*************错误：" + error.getErrorCode() + "*************");
			System.exit(0);
		}

		public void onEvent(int eventType, int arg1, int agr2, String msg) {
		}
		
	}


	// *************************************词表上传*************************************

	/**
	 * 词表上传
	 */
	private void uploadUserWords() {
		SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();
		if (recognizer == null) {
			recognizer = SpeechRecognizer.createRecognizer();

			if (null == recognizer) {
				DebugLog.Log("获取识别实例实败！");
				return;
			}
		}

		UserWords userwords = new UserWords(USER_WORDS);
		recognizer.setParameter(SpeechConstant.DATA_TYPE, "userword");
		recognizer.updateLexicon("userwords", userwords.toString(), lexiconListener);
	}

	/**
	 * 词表上传监听器
	 */
	LexiconListener lexiconListener = new LexiconListener() {
		@Override
		public void onLexiconUpdated(String lexiconId, SpeechError error) {
			if (error == null)
				DebugLog.Log("*************上传成功*************");
			else
				DebugLog.Log("*************" + error.getErrorCode() + "*************");
		}

	};

}
