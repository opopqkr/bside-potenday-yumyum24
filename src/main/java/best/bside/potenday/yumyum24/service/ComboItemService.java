package best.bside.potenday.yumyum24.service;

import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import best.bside.potenday.yumyum24.repository.ComboItemDescriptionRepository;
import best.bside.potenday.yumyum24.repository.ComboItemProductRepository;
import best.bside.potenday.yumyum24.repository.ComboItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboItemService {

    private final ComboItemRepository comboItemRepository;

    private final ComboItemProductRepository comboItemProductRepository;

    private final ComboItemDescriptionRepository comboItemDescriptionRepository;

    public List<ComboItemInfo> getRandomComboItem() {
        List<ComboItemInfo> randomList = comboItemRepository.findOrderByRandom();

        if (randomList != null && !randomList.isEmpty()) {
            for (ComboItemInfo comboItemInfo : randomList) {
                comboItemInfo.setProducts(comboItemProductRepository.findByComboItemId(comboItemInfo.getComboItemId()));
                comboItemInfo.setComboItemDescriptions(comboItemDescriptionRepository.findByComboItemId(comboItemInfo.getComboItemId()));
            }
        }

        return randomList;
    }
}
