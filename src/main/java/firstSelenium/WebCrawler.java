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

public class WebCrawler extends JFrame {	// JFrame ũ�ѷ� Ʋ �������.
	private JTextArea receiver = new JTextArea(23, 70);	// ũ�Ѹ��ؼ� ������ ������ �����ִ� â
	private JTextField sender = new JTextField(10);		// Ű���带 �Է��ϴ� â
	private WebDriver driver;

	public WebCrawler() {
		setTitle("������ ���̹� ���� ũ�ѷ�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(new JLabel("Ű���� �˻�"));

		sender.addActionListener(new MyAction()); // Ű���带 �Է����� �� �����ִ� ActionListener sender�� ���
		c.add(sender);

		c.add(new JScrollPane(receiver));	// ũ�Ѹ��ؼ� ������ ������ �����ִ� â
		setVisible(true);					// ������� JFrame�� Ȱ���� ũ�ѷ� â ����

	}

	private void handleError(String string) { // ����ó��
		System.out.println(string);
		System.exit(1);
	}

	private void setup() throws IOException {  //
		System.setProperty("webdriver.chrome.driver", "C:\\JavaWorkspace\\chromedriver.exe");
											// webdriver�� �ִ� ��ġ ����
		driver = new ChromeDriver();		// ũ������ ���� �˻��� �Ѵ�.
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
						// selenium��  �⺻������ �� �ڿ����� ��� �ε�ɶ����� ��ٷ�������, �Ϲ������� ��� �ڿ��� �ε�ɶ����� ��ٸ��� �ϴ� �ð� ���� ����
		driver.manage().window().maximize();

	}

	private List<Content> searchKeywords(String searchItem) {
		driver.get("https://search.naver.com/search.naver?where=news&sm=tab_jum&query="+searchItem);
												// �Է¹��� Ű����� ���̹����� �������� ũ�Ѹ��Ѵ�.
		String html = driver.getPageSource();   // �������� �ҽ��� �����´�.
		Document doc = Jsoup.parse(html);		// JSoup�� ����  �Ľ��ؼ� ������ �����´�.
		
		Elements contents = doc.select("li[id^=sp_nw]");
		String title; // ��� ����
		String description;	// �󼼼���
		String address;  // ��� ��ó
		String photoAddress; // �����ּ�
		
		List<Content> nList = new ArrayList<Content>();
		
		for (Element e : contents) {
			title = e.getElementsByClass("_sp_each_title").attr("title"); // ������� ��������
			description = e.getElementsByTag("dd").get(1).text(); // �󼼼��� ��������
			address = e.getElementsByClass("sp_thmb thmb80").attr("href"); //��� ��ó ��������
			photoAddress = e.getElementsByTag("img").attr("src"); // �����ּ� ��������
			
			nList.add(new Content(title, description, address, photoAddress));
			receiver.append("�������  :  "+title + "\n");
			receiver.append("�󼼼���  :  "+description + "\n");
			receiver.append("�����ó  :  "+address + "\n");
			receiver.append("�����ּ�  :  "+photoAddress + "\n\n\n"); // ������ �������� JFrame TextArea�� ����.
			
		}

		return nList;
	}

	class MyAction implements ActionListener {			// sender���� Ű���带 �Է��ϸ�  Ationlistener�� ����ȴ�.
		public void actionPerformed(ActionEvent e) {
			String keyWord = sender.getText();
			try {
				setup();					// �¾�
				searchKeywords(keyWord);	// Ű���带 ������ ũ�Ѹ��ϴ� �۾�
			} catch (IOException e1) {
				handleError(e1.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		new WebCrawler();

	}

}
