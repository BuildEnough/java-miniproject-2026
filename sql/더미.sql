-- 자유게시판 게시글 30개
INSERT INTO C_BOARD (
    POST_ID,
    TITLE,
    CONTENTS,
    WRITER,
    ANONYMOUS,
    VIEW_COUNT,
    CREATED_AT,
    USER_ID
)
SELECT
    C_BOARD_SEQ.NEXTVAL,
    CASE
        WHEN MOD(LEVEL, 5) = 1 THEN '오늘 개발 공부한 내용 공유합니다 ' || LEVEL
        WHEN MOD(LEVEL, 5) = 2 THEN '프로젝트 발표 준비 중입니다 ' || LEVEL
        WHEN MOD(LEVEL, 5) = 3 THEN '자유게시판 페이징 테스트 글 ' || LEVEL
        WHEN MOD(LEVEL, 5) = 4 THEN '스프링 부트 어렵지만 재밌네요 ' || LEVEL
        ELSE '팀 프로젝트 진행 상황 공유 ' || LEVEL
    END,
    '자유게시판 기능 테스트를 위한 더미 게시글입니다. 글 번호: ' || LEVEL,
    'pknu',
    CASE
        WHEN MOD(LEVEL, 4) = 0 THEN 1
        ELSE 0
    END,
    MOD(LEVEL * 3, 40),
    CURRENT_DATE - LEVEL,
    1
FROM DUAL
CONNECT BY LEVEL <= 30;

COMMIT;




-------- 자유 게시판 댓글 30개

INSERT INTO REPLY (
    REPLY_ID,
    CONTENTS,
    WRITER,
    CREATED_AT,
    POST_ID,
    USER_ID
)
SELECT
    SEQ_REPLY_ID.NEXTVAL,
    CASE
        WHEN MOD(RN, 4) = 1 THEN '좋은 글 감사합니다.'
        WHEN MOD(RN, 4) = 2 THEN '저도 비슷하게 느꼈습니다.'
        WHEN MOD(RN, 4) = 3 THEN '발표 준비 잘 되길 바랍니다.'
        ELSE '댓글 기능 테스트입니다.'
    END,
    'pknu',
    CURRENT_DATE - (RN / 24),
    POST_ID,
    1
FROM (
    SELECT
        ROWNUM AS RN,
        POST_ID
    FROM (
        SELECT POST_ID
        FROM C_BOARD
        ORDER BY POST_ID DESC
    )
    WHERE ROWNUM <= 20
);

COMMIT;

-- 거래 게시글 30개
INSERT INTO B_BOARD (
    POST_ID,
    CATEGORY_ID,
    TITLE,
    CONTENTS,
    VIEW_COUNT,
    IMAGE_PATH,
    CREATED_AT,
    USER_ID
)
SELECT
    B_BOARD_SEQ.NEXTVAL,
    MOD(LEVEL - 1, 4) + 1,
    CASE MOD(LEVEL - 1, 4)
        WHEN 0 THEN '노트북 거치대 삽니다 ' || LEVEL
        WHEN 1 THEN '기계식 키보드 팝니다 ' || LEVEL
        WHEN 2 THEN '모니터 예약중입니다 ' || LEVEL
        ELSE '무선 마우스 판매완료 ' || LEVEL
    END,
    '거래게시판 검색, 페이징, 카테고리, 댓글 기능 테스트를 위한 더미 게시글입니다. 글 번호: ' || LEVEL,
    MOD(LEVEL * 2, 50),
    NULL,
    CURRENT_DATE - LEVEL,
    1
FROM DUAL
CONNECT BY LEVEL <= 30;

COMMIT;

-- 거래 게시판 댓글 30개
INSERT INTO COMMENTS (
    COMMENT_ID,
    CONTENTS,
    CREATED_AT,
    POST_ID,
    CATEGORY_ID,
    USER_ID
)
SELECT
    COMMENTS_SEQ.NEXTVAL,
    CASE
        WHEN MOD(RN, 4) = 1 THEN '아직 거래 가능할까요?'
        WHEN MOD(RN, 4) = 2 THEN '가격 조정 가능하신가요?'
        WHEN MOD(RN, 4) = 3 THEN '직거래 위치가 어디인가요?'
        ELSE '상세 상태가 궁금합니다.'
    END,
    CURRENT_DATE - (RN / 24),
    POST_ID,
    CATEGORY_ID,
    1
FROM (
    SELECT
        ROWNUM AS RN,
        POST_ID,
        CATEGORY_ID
    FROM (
        SELECT POST_ID, CATEGORY_ID
        FROM B_BOARD
        ORDER BY POST_ID DESC
    )
    WHERE ROWNUM <= 20
);

COMMIT;