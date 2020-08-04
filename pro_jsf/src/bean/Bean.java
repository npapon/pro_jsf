package bean;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.http.Part;

public class Bean {
    private Part   file;
    private String fileContent;

    public void upload() {
        try {
            fileContent = new Scanner( file.getInputStream() )
                    .useDelimiter( "\\A" ).next();
        } catch ( IOException e ) {
            // Error handling
        }
    }

    public Part getFile() {
        return file;
    }

    public void setFile( Part file ) {
        this.file = file;
    }
}