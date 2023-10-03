-- 코드를 입력하세요
SELECT T.CART_ID 
FROM(
    SELECT CART_ID, GROUP_CONCAT(NAME) NAMES
    FROM CART_PRODUCTS
    GROUP BY CART_ID
) T
WHERE T.NAMES LIKE '%MILK%' AND T.NAMES LIKE '%YOGURT%'
ORDER BY CART_ID;

-- 우유, 요거트 동시에 구입한 장바구니가 있는지?
-- 동시에 구입한 장바구니 아이디 조회