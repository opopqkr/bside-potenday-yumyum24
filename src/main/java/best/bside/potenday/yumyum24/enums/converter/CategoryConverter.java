package best.bside.potenday.yumyum24.enums.converter;

import best.bside.potenday.yumyum24.enums.Category;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter extends CodeValueConverter<Category> {
    CategoryConverter() {
        super(Category.class);
    }
}
