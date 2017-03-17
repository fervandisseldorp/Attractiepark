package B2a.controller;

import B2a.domain.NewsMessage.Member;
import B2a.domain.NewsMessage.NewsMessage;
import B2a.domain.Ticket.Ticket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Controller
public class NewsMessageController {

    @RequestMapping(value = "/newsmessage", method = RequestMethod.GET)
    public ModelAndView newsmessage(NewsMessage newsMessage) {
        return new ModelAndView("newsmessage", "newsmessage", null);
    }

    @RequestMapping(value = "/newsmessage", method = RequestMethod.POST)
    public ModelAndView newsmessage() {

        System.out.println("Doet iets");

            String subject = "Winter sales";

            String content = "<b>Greetings,</b><br><br>";
            content += "Winter sales are coming and you can be the first the profit from it.<br>";
            content += "Head quick to our website to see what we can offer you!<br><br>";
            content += "-The AttractieparkB2a team";

            NewsMessage message = new NewsMessage(subject, content);

            new Member("Niels", "Kerdel", new Date(26-2-1996), "Dr. blomsingel 31", "Krimpen aan den IJssel", "2922CD", "NielsKerdel", "1234", "nskerdel@hotmail.com", true, message);
            new Member("Bart", "Helleman", new Date(2-9-1996), "Schuwacht", "Lekkerkerk", "1234AA", "BartHelleman", "1234", "nielskerdel1996@gmail.com", false, message);


            List<String> emails = message.notifyMembers();

            if(!emails.isEmpty()) {
                sendNewsLetter(emails, subject, content);
            }
            System.out.println("Should have worked");

        return new ModelAndView("newsletter", "newsMessage", null);
    }

    private void sendNewsLetter(List<String> email, String subject, String content) {
        final String username = "AttractieparkB2a@gmail.com";
        final String password = "B2aAttractiepar";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "*");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("AttractieparkB2a@gmail.com"));
            for (String anEmail : email) {
                message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(anEmail));
            }

            message.setSubject(subject);
            message.setContent(content, "text/html");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}