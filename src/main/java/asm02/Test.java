package asm02;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static List<String> existingFiles = Arrays.asList("test(1).pdf", "test2.pdf");
    public static void main(String[] args) {
        String file = "test.pdf";
        save(file);

    }
    public static String save(String file){
        String savedFileName = file;
        String ext = file.substring(file.lastIndexOf("."));
        System.out.println(savedFileName.substring(0, savedFileName.lastIndexOf(".")));
        System.out.println(ext);
//        Pattern pattern = Pattern.compile("(.*)\\((\\d+)\\)\\.pdf$");
        Pattern pattern = Pattern.compile("(.*?)(?:\\((\\d+)\\))?"+ext+"$");
        Matcher match = pattern.matcher(savedFileName);
        if(match.matches()){
            String coreName = match.group(1);
            String duplicateSuffix = match.group(2);
            Integer duplVersion = existingFiles.stream()
                    .filter(f->f.matches(
                            String.valueOf(Pattern.compile(coreName+"(?:\\((\\d+)\\))?\\.pdf$"))
                    ))
                    .map(f->{
                        Matcher m = pattern.matcher(f);
                        if(m.matches() && m.group(2)!=null){
                            return Integer.valueOf(m.group(2));
                        }
                        return 0;
                    })
                    .max(Integer::compareTo).orElse(-1);
            duplVersion++;
            if(duplVersion>0){
                savedFileName = coreName+"("+duplVersion+")";
            }
        }else
            System.out.println("no match");



        System.out.println("SAVED UNDER NAME ["+savedFileName+"]");
        return savedFileName;
    }
}
