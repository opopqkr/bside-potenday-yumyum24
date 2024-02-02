package best.bside.potenday.yumyum24.service;

import best.bside.potenday.yumyum24.enums.Category;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import best.bside.potenday.yumyum24.payload.responses.RecommendComboItem;
import best.bside.potenday.yumyum24.repository.ComboItemDescriptionRepository;
import best.bside.potenday.yumyum24.repository.ComboItemProductRepository;
import best.bside.potenday.yumyum24.repository.ComboItemRepository;
import best.bside.potenday.yumyum24.repository.RecommedMentRepository;
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

    public List<ComboItemInfo> getRandomComboItem() {
        final List<ComboItemInfo> randomList = comboItemRepository.findOrderByRandom();

        if (randomList != null && !randomList.isEmpty()) {
            for (ComboItemInfo comboItemInfo : randomList) {
                comboItemInfo.setProducts(comboItemProductRepository.findProductByComboItemId(comboItemInfo.getComboItemId()));
                comboItemInfo.setComboItemDescriptions(comboItemDescriptionRepository.findByComboItemId(comboItemInfo.getComboItemId()));
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
}
