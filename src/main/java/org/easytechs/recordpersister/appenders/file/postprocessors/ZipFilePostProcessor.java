package org.easytechs.recordpersister.appenders.file.postprocessors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.easytechs.recordpersister.appenders.file.FilePostProcessor;


public class ZipFilePostProcessor implements FilePostProcessor {

	private Charset charset = Charset.forName("UTF-8");
	@Override
	public void postProcess(Path path) {
		BufferedWriter zipBuffered = null;
		BufferedReader originalFileReader = null;
		try {
			Path zipPath = Paths.get(path.toString() + ".zip");
			FileOutputStream fos = new FileOutputStream(zipPath.toFile());
			ZipOutputStream zos = new ZipOutputStream(fos);
			ZipEntry ze = new ZipEntry(path.getFileName().toString());
			zos.putNextEntry(ze);
			zipBuffered = new BufferedWriter(new OutputStreamWriter(zos));
			originalFileReader = Files.newBufferedReader(path, charset);
			String line = null;
			while((line=originalFileReader.readLine())!=null){
				zipBuffered.write(line+"\n");
			}		
			path.toFile().delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				zipBuffered.close();
			} catch (Exception e) {
				//Nothing to do
			}
			try {
				originalFileReader.close();
			} catch (Exception e) {
				//Nothing to do
			}	
		}
	}

}
