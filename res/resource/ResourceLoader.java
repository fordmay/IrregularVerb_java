package resource;

import java.awt.Image;
import java.awt.Toolkit;

public class ResourceLoader {
	
	static ResourceLoader rl = new ResourceLoader();
	/* https://www.youtube.com/watch?v=rCoed3MKpEA
	 * http://www.codexpedia.com/java/java-packaging-resource-files-into-a-runnable-jar-file/
	 */
	public static Image loadImage(String imageName){
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource("images/"+imageName));
	}
}
