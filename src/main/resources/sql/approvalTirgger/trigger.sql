use itam;
-- 기존 트리거 삭제
DROP TRIGGER IF EXISTS trg_use_history_before_insert;
DROP TRIGGER IF EXISTS trg_use_history_before_update;

DELIMITER $$

-- INSERT 시 자동 보정
CREATE TRIGGER trg_use_history_before_insert
    BEFORE INSERT ON tb_use_history
    FOR EACH ROW
BEGIN
    DECLARE v_productNo VARCHAR(50);
    DECLARE v_productName VARCHAR(255);

    -- productNo 먼저 세팅 (detail 기준)
    IF NEW.productNo IS NULL OR NEW.productNo = '' THEN
        SELECT d.product_no
        INTO v_productNo
        FROM tb_product_detail d
        WHERE d.category_code = NEW.category_code
          AND d.product_detail_code = NEW.product_detail_code
        LIMIT 1;

        SET NEW.productNo = v_productNo;
    ELSE
        SET v_productNo = NEW.productNo;
    END IF;

    -- productName 세팅 (info 기준)
    IF NEW.productName IS NULL OR NEW.productName = '' THEN
        SELECT i.product_name
        INTO v_productName
        FROM tb_product_info i
        WHERE i.product_no = v_productNo
        LIMIT 1;

        SET NEW.productName = v_productName;
    END IF;
END$$


-- UPDATE 시 자동 보정
CREATE TRIGGER trg_use_history_before_update
    BEFORE UPDATE ON tb_use_history
    FOR EACH ROW
BEGIN
    DECLARE v_productNo VARCHAR(50);
    DECLARE v_productName VARCHAR(255);

    -- productNo 먼저 세팅
    IF NEW.productNo IS NULL OR NEW.productNo = '' THEN
        SELECT d.product_no
        INTO v_productNo
        FROM tb_product_detail d
        WHERE d.category_code = NEW.category_code
          AND d.product_detail_code = NEW.product_detail_code
        LIMIT 1;

        SET NEW.productNo = v_productNo;
    ELSE
        SET v_productNo = NEW.productNo;
    END IF;

    -- productName 세팅
    IF NEW.productName IS NULL OR NEW.productName = '' THEN
        SELECT i.product_name
        INTO v_productName
        FROM tb_product_info i
        WHERE i.product_no = v_productNo
        LIMIT 1;

        SET NEW.productName = v_productName;
    END IF;
END$$

DELIMITER ;


show triggers like 'tb_use_history';