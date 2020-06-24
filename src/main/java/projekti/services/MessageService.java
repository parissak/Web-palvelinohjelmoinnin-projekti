package projekti.services;

import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.models.Account;
import projekti.models.Message;
import projekti.models.MessageComment;
import projekti.repositories.MessageCommentRepository;
import projekti.repositories.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageCommentRepository messageCommentRepository;

    @Transactional
    public List<Message> findAssociatedTopSortedMessages(Account account) {
        return messageRepository.findTop25ByPosterOrPosterInOrderByStampDesc(account, account.getConnections());
    }

    public void updateMessage(Message message) {
        messageRepository.save(message);
    }

    public void saveMessage(String messageText, Account poster) {
        Message message = new Message();
        message.setMessage(messageText);
        message.setStamp(LocalDateTime.now());
        message.setPoster(poster);
        updateMessage(message);
    }

    public Message getOneMessage(Long messageId) {
        return messageRepository.getOne(messageId);
    }

    public void saveComment(MessageComment messageComment) {
        messageCommentRepository.save(messageComment);
    }

    public void saveComment(String description, Account poster, Message message) {
        MessageComment messageComment = new MessageComment();
        messageComment.setComment(description);
        messageComment.setPoster(poster);
        messageComment.setStamp(LocalDateTime.now());
        messageComment.setMessage(message);
        saveComment(messageComment);
    }
}