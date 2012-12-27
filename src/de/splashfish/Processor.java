package de.splashfish;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;

import org.pegdown.PegDownProcessor;

import de.splashfish.common.FileUtility;

public class Processor implements Runnable {

	private PegDownProcessor pdp;
	private ProcessorStateListener listener;
	
	private String title;
	private File dest;
	private File[] src;
	private File css;
	
	public Processor(String title, File dest, File[] src, File css, ProcessorStateListener processorStateListener) {
		this.pdp = new PegDownProcessor();
		this.listener = processorStateListener;
		
		this.title = title;
		this.dest  = dest;
		this.src   = src;
		this.css   = css;
	
		Arrays.sort(this.src);
	}

	@Override
	public void run() {
		if(!dest.exists() || !dest.isDirectory())
			dest.mkdirs();
		
		new File(dest, "res").mkdirs();
		try {
			FileUtility.writeFully(new File(dest, "res/style.css"), FileUtility.readFully(this.css));
			FileUtility.writeFully(new File(dest, "res/script.js"), FileUtility.readFully(new File(Processor.class.getResource("/generate-template/script.js").toURI())));
		} catch (IOException | URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String navigation = "";
		for(File file : src) {
			String element = file.getName().replace(".mdown", "");
			try {
				navigation += "<li><a href=\""+ URLEncoder.encode(element, "UTF-8").replace("+", "%20") +".html\">"+ element +"</a></li>\n";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int i = 0;
		for(File file : src) {
			try {
				this.listener.stateChanged(file.getName(), (int)((++i/(double)src.length)*100));
				
				String template = FileUtility.readFully(new File(this.getClass().getResource("/generate-template/index.html").toURI()));
				template = template.replace("%content%", pdp.markdownToHtml(FileUtility.readFully(file)))
						           .replace("%title%", file.getName().replace(".mdown", "") +" | "+ this.title)
								   .replace("%navigation%", navigation);
				FileUtility.writeFully(new File(dest, file.getName().replace(".mdown", ".html")),
									   template
									  );
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static interface ProcessorStateListener {
		public void stateChanged(String desc, int percent);
	}
	
}
