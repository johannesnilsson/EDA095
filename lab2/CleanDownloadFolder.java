import java.io.File;

public class CleanDownloadFolder {

	public static void main(String[] args) {

		File file = new File("../EDA095/DownloadedFile");
		System.out.print("Deleting files: " + file.exists());
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			for (int i = 0; i < myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]);
				myFile.delete();
			}
		}
	}
}
