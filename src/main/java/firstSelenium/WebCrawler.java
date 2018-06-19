package firstSelenium;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebCrawler extends JFrame {	// JFrame 크롤러 틀 만들었다.
	private JTextArea receiver = new JTextArea(23, 70);	// 크롤링해서 가져온 정보를 보여주는 창
	private JTextField sender = new JTextField(10);		// 키워드를 입력하는 창
	private WebDriver driver;

	public WebCrawler() {
		setTitle("차농의 네이버 뉴스 크롤러");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(new JLabel("키워드 검색"));

		sender.addActionListener(new MyAction()); // 키워드를 입력했을 때 보내주는 ActionListener sender에 등록
		c.add(sender);

		c.add(new JScrollPane(receiver));	// 크롤링해서 가져온 정보를 보여주는 창
		setVisible(true);					// 여기까지 JFrame을 활용한 크롤러 창 설정

	}

	private void handleError(String string) { // 에러처리
		System.out.println(string);
		System.exit(1);
	}

	private void setup() throws IOException {  //
		System.setProperty("webdriver.chrome.driver", "C:\\JavaWorkspace\\chromedriver.exe");
											// webdriver가 있는 위치 설정
		driver = new ChromeDriver();		// 크롬으로 정보 검색을 한다.
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
						// selenium이  기본적으로 웹 자원들이 모두 로드될때까지 기다려주지만, 암묵적으로 모든 자원이 로드될때까지 기다리게 하는 시간 직접 설정
		driver.manage().window().maximize();

	}

	private List<Content> searchKeywords(String searchItem) {
		driver.get("https://search.naver.com/search.naver?where=news&sm=tab_jum&query="+searchItem);
												// 입력받은 키워드로 네이버에서 페이지를 크롤링한다.
		String html = driver.getPageSource();   // 페이지의 소스를 가져온다.
		Document doc = Jsoup.parse(html);		// JSoup을 통해  파싱해서 정보를 가져온다.
		
		Elements contents = doc.select("li[id^=sp_nw]");
		String title; // 기사 제목
		String description;	// 상세설명
		String address;  // 기사 출처
		String photoAddress; // 사진주소
		
		List<Content> nList = new ArrayList<Content>();
		
		for (Element e : contents) {
			title = e.getElementsByClass("_sp_each_title").attr("title"); // 기사제목 가져오기
			description = e.getElementsByTag("dd").get(1).text(); // 상세설명 가져오기
			address = e.getElementsByClass("sp_thmb thmb80").attr("href"); //기사 출처 가져오기
			photoAddress = e.getElementsByTag("img").attr("src"); // 사진주소 가져오기
			
			nList.add(new Content(title, description, address, photoAddress));
			receiver.append("기사제목  :  "+title + "\n");
			receiver.append("상세설명  :  "+description + "\n");
			receiver.append("기사출처  :  "+address + "\n");
			receiver.append("사진주소  :  "+photoAddress + "\n\n\n"); // 가져온 정보들을 JFrame TextArea에 띄운다.
			
		}

		return nList;
	}

	class MyAction implements ActionListener {			// sender에서 키워드를 입력하면  Ationlistener가 실행된다.
		public void actionPerformed(ActionEvent e) {
			String keyWord = sender.getText();
			try {
				setup();					// 셋업
				searchKeywords(keyWord);	// 키워드를 가지고 크롤링하는 작업
			} catch (IOException e1) {
				handleError(e1.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		new WebCrawler();

	}

}
