# 자사몰 결제 API 시스템

## 소개

자사몰 간편결제를 위한 결제대행 api

## 데이터베이스 구조

### 결제 (Payments) 테이블

- `payment_id` (고유 결제 식별자)
- `user_id` (사용자 식별자)
- `amount` (결제 금액)
- `status` (결제 상태: completed, failed, canceled, refunded 등)
- `transaction_number` (거래 번호 또는 고유 키)
- `order_date` (주문 일자)
- `payment_method` (사용된 결제 수단)
- `payment_method_details` (결제 수단 세부 정보)
- `결제자 이름` (결제자 이름)
- `결제자 이메일` (결제자 이메일 주소)
- `카드사` (사용된 카드사)
- `내용` (결제 내용)
- `상품명` (결제된 상품명)
- `승인번호` (결제 승인 번호)
- `할부기간` (결제 할부 기간)
- `카드번호` (마스킹된 카드 번호)
- 기타 필드: 필요에 따라 추가적인 필드를 포함할 수 있음

## 결제 성공 (Payment Success)

- 내용 코드 번호: 100
- 내용: "결제가 성공적으로 완료되었습니다. 감사합니다!"

## 결제 실패 (Payment Fail)

| 에러 코드               | 한글 에러 메시지
|--------------------------|---------------------------------------------------
| BELOW_ZERO_AMOUNT        | 금액은 0보다 커야 합니다.
| DUPLICATED_ORDER_ID      | 이미 승인 및 취소가 진행된 중복된 주문번호 입니다. 다른 주문번호로 진행해주세요.
| INVALID_BANK             | 유효하지 않은 은행입니다.
| INVALID_CARD_COMPANY     | 유효하지 않은 카드사입니다.
| INVALID_SUCCESS_URL      | successUrl 값은 필수 값입니다.
| INVALID_EMAIL            | 이메일 주소 형식에 맞지 않습니다.
| INVALID_DATE             | 날짌 데이터가 잘못 되었습니다.
| INCORRECT_FAIL_URL_FORMAT | 잘못된 failUrl 입니다.
| INCORRECT_SUCCESS_URL_FORMAT | 잘못된 successUrl 입니다.
| INVALID_ORDER_ID         | orderId는 영문 대소문자, 숫자, 특수문자(-, _) 만 허용합니다. 6자 이상 64자 이하여야 합니다.
