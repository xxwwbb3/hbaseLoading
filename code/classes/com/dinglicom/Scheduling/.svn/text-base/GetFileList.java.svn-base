package com.dinglicom.Scheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author panzhen.jack@gmail.com
 * 
 */
public class GetFileList {
	private final static Logger LOG = Logger.getLogger(GetFileList.class);

	public List<String> submitSH(String cmd) {
		Process proc = null;
		List<String> fileList = new ArrayList<String>();
		int exitCode = -1;
		TrackConsoleMsg trackInf = null;
		try {
			proc = new ProcessBuilder("bash", "-c", cmd).start();
			trackInf = new TrackConsoleMsg(proc.getInputStream(), fileList);
			trackInf.start();
			exitCode = proc.waitFor();
			if (exitCode != 0 && exitCode != 2) {
				LOG.warn("Process invoke error code: " + exitCode);
			}
			trackInf.join();
		} catch (IOException e) {
			LOG.warn("Process invoke IOException:", e);
		} catch (InterruptedException e) {
			LOG.warn("Process invoke Interrupted Exception:", e);
		} finally {
			proc.destroy();
		}
		return fileList;
	}

	class TrackConsoleMsg extends Thread {
		private BufferedReader read = null;
		private List<String> list = null;

		public TrackConsoleMsg(InputStream in, List<String> list) {
			read = new BufferedReader(new InputStreamReader(in));
			this.list = list;
		}

		@Override
		public void run() {
			String line = null;
			try {
				line = read.readLine();
				while (null != line && !isInterrupted()) {
					if(line.indexOf("ls: cannot access") < 0) {
						list.add(line);
					}
					line = read.readLine();
				}
			} catch (Exception e) {
				LOG.error("Read console error:", e);
			} finally {
				if(null != read) {
					try {
						read.close();
					} catch (IOException e) {
						LOG.error("Read console error:", e);
					}
				}
			}
		}
	}
}
