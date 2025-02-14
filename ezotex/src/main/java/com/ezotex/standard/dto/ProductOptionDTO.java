package com.ezotex.standard.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=true) // 상속관계에서 @Data가 중복되어 넣음
@Data
@NoArgsConstructor
public class ProductOptionDTO extends ProductCategoryDTO {
	
	protected String optionCode;
	protected String productCode;
	protected String productColor;
	protected String productSize;
	protected Integer unitPrice;
	protected String discontinued;
	
}
