-- 코드를 입력하세요
SELECT CATEGORY, PRICE MAX_PRICE, PRODUCT_NAME
FROM FOOD_PRODUCT
WHERE (CATEGORY, PRICE) IN (
    SELECT CATEGORY, MAX(PRICE) PRICE
    FROM FOOD_PRODUCT
    GROUP BY CATEGORY
    HAVING CATEGORY IN ('과자', '국', '김치', '식용유')
    )
ORDER BY MAX_PRICE DESC;

-- 식품분류별로 제일 비싼 식품의 분류, 가격, 이름 조회
-- 과자, 국, 김치, 식용유인 경우만 출력
-- 결과: 식품 가격 기준 내림차순