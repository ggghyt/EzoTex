package com.ezotex.standard.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=true) // 상속관계에서 @Data가 중복되어 넣음
@Data
@NoArgsConstructor
public class ProductDTO extends ProductOptionDTO {	
	
	private String productCode;
	private String productName;
	private String productType;
	private Integer unitPrice;
	private Integer vl;
	private String img;
	private String unitName;

	// Lombok에서는 필드 상속 구현에 제약이 있어, 직접 생성자 메소드 넣음
	public ProductDTO(String productCode, String productName, String productType, Integer unitPrice, Integer vl,
			String img, String unitName) {
		super(); // 옵션, 카테고리 모두 포함
		this.productCode = productCode;
		this.productName = productName;
		this.productType = productType;
		this.unitPrice = unitPrice;
		this.vl = vl;
		this.img = img;
		this.unitName = unitName;
	}

	@Override
	public String toString() {
		return "ProductDTO [productCode=" + productCode + ", productName=" + productName + ", productType="
				+ productType + ", unitPrice=" + unitPrice + ", vl=" + vl + ", img=" + img + ", unitName=" + unitName
				+ ", optionCode=" + optionCode + ", productColor=" + productColor + ", productSize=" + productSize
				+ ", discontinued=" + discontinued + ", lclas=" + lclas + ", sclas=" + sclas + "]";
	}
	
}
