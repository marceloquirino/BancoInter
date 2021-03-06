package com.marcelo.inter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames={"soma"})
@Entity
public class SingleUnit {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	
	@Column(nullable=false)
	private Integer id;
	
	@Column(nullable=false)
	private String stringNumber;
	
	@Column(nullable=false)
	private Integer numberTimes;
	
	@Column(nullable=false)
	private Integer result;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStringNumber() {
		return stringNumber;
	}

	public void setStringNumber(String stringNumber) {
		this.stringNumber = stringNumber;
	}

	public Integer getNumberTimes() {
		return numberTimes;
	}

	public void setNumberTimes(Integer numberTimes) {
		this.numberTimes = numberTimes;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public void setResultByCalculation() {
		this.setResult(calculateSingleDigit(concatStringByNumber()));
	}
	
	@Cacheable
	public static int soma(int a, int b) {
		simulateSlowService();
		return a+b;
	}
	
	private static void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
	
	
	private Integer calculateSingleDigit(String stringNumberForCalculation) {
		Integer calculationResult = 0;
		for(int i=0; i < stringNumberForCalculation.length(); i++) {
			calculationResult += Integer.parseInt(stringNumberForCalculation.substring(i, i+1));
		}
		if(calculationResult>9) {
			return calculateSingleDigit(calculationResult.toString());
		}
		return calculationResult;
	}
	
	private String concatStringByNumber() {
		String result = "";
		for(int i=0; i < this.numberTimes; i++) {
			result = result + this.stringNumber;
		}
		return result;
	}
}
