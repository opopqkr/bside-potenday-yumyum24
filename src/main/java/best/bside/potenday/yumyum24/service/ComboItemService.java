package best.bside.potenday.yumyum24.service;

import best.bside.potenday.yumyum24.domain.ComboItemDescription;
import best.bside.potenday.yumyum24.domain.Reply;
import best.bside.potenday.yumyum24.enums.Category;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.requests.ReplyRequest;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import best.bside.potenday.yumyum24.payload.responses.RecommendComboItem;
import best.bside.potenday.yumyum24.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<ComboItemInfo> getRandomComboItem() {
        final List<ComboItemInfo> randomList = comboItemRepository.findOrderByRandom();

        if (randomList != null && !randomList.isEmpty()) {
            for (ComboItemInfo comboItemInfo : randomList) {
                comboItemInfo.setProducts(comboItemProductRepository.findProductByComboItemId(comboItemInfo.getComboItemId()));
            }
        }

        return randomList;
    }

    public RecommendComboItem getRecommendComboItem() {
        ComboItemInfo foodComboItem = comboItemRepository.findByCategory(Category.FOOD);
        if (foodComboItem != null) {
            foodComboItem.setProducts(comboItemProductRepository.findProductByComboItemId(foodComboItem.getComboItemId()));
        }

        ComboItemInfo drinkComboItem = comboItemRepository.findByCategory(Category.DRINK);
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
        if (list != null && !list.isEmpty()) {
            for (ComboItemInfo comboItemInfo : list) {
                comboItemInfo.setProducts(comboItemProductRepository.findProductByComboItemId(comboItemInfo.getComboItemId()));
            }
        }

        return comboItemInfoPage;
    }

    public List<ComboItemDescription> getComboItemDescription(Long id) {
        return comboItemDescriptionRepository.findByComboItemIdOrderByOrderNumber(id);
    }

    public Page<Reply> getComboItemReply(Long id, PageInfo pageInfo) {
        return replyRepository.findByPageInfo(id, pageInfo);
    }

    public void writeReply(Long comboItemId, String userName, ReplyRequest replyRequest) {
        Reply reply = replyRequest.toEntity(comboItemId, userName);
        reply.completeIssueReply();
        replyRepository.save(reply);
    }
}
