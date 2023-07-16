package busanhackathon.team4.gptApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class gptApiService {

    public String getCompletion(String prompt) {
        return "0";
    }
    public static void main(String[] args) {
        try {
            // 파이썬 실행 명령과 파일 경로
            String command = "python";
            String filePath = "src/main/java/busanhackathon/team4/gptApi/gptApi.py";
            String query = "김치\n두부\n쉬움";


            ProcessBuilder processBuilder = new ProcessBuilder(command, filePath, query);
            Process process = processBuilder.start();

            // 실행 결과 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));

            // 오류 읽기
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));


            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // 실행 결과 출력
            }
            while ((line = errorReader.readLine()) != null) {
                System.out.println(line);
            }

            // 프로세스 종료 대기
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}