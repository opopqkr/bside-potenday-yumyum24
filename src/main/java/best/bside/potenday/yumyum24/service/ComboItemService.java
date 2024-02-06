package best.bside.potenday.yumyum24.service;

import best.bside.potenday.yumyum24.domain.ComboItemDescription;
import best.bside.potenday.yumyum24.domain.Reply;
import best.bside.potenday.yumyum24.domain.User;
import best.bside.potenday.yumyum24.enums.Category;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.requests.ReplyRequest;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import best.bside.potenday.yumyum24.payload.responses.RecommendComboItem;
import best.bside.potenday.yumyum24.payload.responses.ReplyInfo;
import best.bside.potenday.yumyum24.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboItemService {

    private final ComboItemRepository comboItemRepository;

    private final ComboItemProductRepository comboItemProductRepository;

    private final ComboItemDescriptionRepository comboItemDescriptionRepository;

    private final RecommedMentRepository recommedMentRepository;

    private final ReplyRepository replyRepository;

    private final UserRepository userRepository;

    public List<ComboItemInfo> getRandomComboItem() {
        final List<ComboItemInfo> randomList = comboItemRepository.findOrderByRandom();

        if (ObjectUtils.isNotEmpty(randomList)) {
            for (ComboItemInfo comboItemInfo : randomList) {
                comboItemInfo.setProducts(comboItemProductRepository.findProductByComboItemId(comboItemInfo.getComboItemId()));
            }
        }

        return randomList;
    }

    public RecommendComboItem getRecommendComboItem() {
        final ComboItemInfo foodComboItem = comboItemRepository.findByCategory(Category.FOOD);
        if (foodComboItem != null) {
            foodComboItem.setProducts(comboItemProductRepository.findProductByComboItemId(foodComboItem.getComboItemId()));
        }

        final ComboItemInfo drinkComboItem = comboItemRepository.findByCategory(Category.DRINK);
        if (drinkComboItem != null) {
            drinkComboItem.setProducts(comboItemProductRepository.findProductByComboItemId(drinkComboItem.getComboItemId()));
        }

        return RecommendComboItem.builder()
                .ment(recommedMentRepository.findRecommedMentByNow(LocalTime.now()))
                .foodComboItem(foodComboItem)
                .drinkComboItem(drinkComboItem)
                .build();
    }

    public Page<ComboItemInfo> getComboItem(String category, String sortBy, PageInfo pageInfo) {
        final Page<ComboItemInfo> comboItemInfoPage
                = comboItemRepository.findByCategoryOrderBySortByPageInfo(category, sortBy, pageInfo);

        final List<ComboItemInfo> list = comboItemInfoPage.getResult();
        if (ObjectUtils.isNotEmpty(list)) {
            for (ComboItemInfo comboItemInfo : list) {
                comboItemInfo.setProducts(comboItemProductRepository.findProductByComboItemId(comboItemInfo.getComboItemId()));
            }
        }

        return comboItemInfoPage;
    }

    public List<ComboItemDescription> getComboItemDescription(Long id) {
        return comboItemDescriptionRepository.findByComboItemIdOrderByOrderNumber(id);
    }

    public Page<ReplyInfo> getComboItemReply(String email, Long comboItemId, PageInfo pageInfo) {
        final User user = userRepository.findByEmail(email);

        return user == null ? replyRepository.findByPageInfo(comboItemId, pageInfo) :
                replyRepository.findByPageInfo(user.getUserId(), comboItemId, pageInfo);
    }

    @Transactional
    public void writeReply(String email, Long comboItemId, ReplyRequest replyRequest) {
        final User user = userRepository.findByEmail(email);

        Reply reply = replyRequest.toEntity(comboItemId, user.getUserId());
        reply.completeIssueReply();
        replyRepository.save(reply);
        comboItemRepository.updateComboItemReplyCount(comboItemId, 1L);
    }

    @Transactional
    public void updateReply(Long replyId, ReplyRequest replyRequest) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new NoResultException("해당 댓글은 삭제되었습니다."));

        reply.setContent(replyRequest.getContent());
        reply.updateReply();
        replyRepository.save(reply);
    }

    @Transactional
    public void deleteReply(Long ComboItemId, Long replyId) {
        final Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new NoResultException("해당 댓글은 삭제되었습니다."));

        replyRepository.delete(reply);
        comboItemRepository.updateComboItemReplyCount(ComboItemId, -1L);
    }
}
