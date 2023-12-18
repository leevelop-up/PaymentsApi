# 자사몰 결제 API 시스템
[![CI/CD for PaymentsApi](https://github.com/leevelop-up/PaymentsApi/actions/workflows/workflows.yml/badge.svg)](https://github.com/leevelop-up/PaymentsApi/actions/workflows/workflows.yml)
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

결제 실패시 Logger에 저장하여 기록저장 그외 성공시 DB저장하여 목록와
![image](https://github.com/leevelop-up/PaymentsApi/assets/63052631/f45d03a1-880c-4caa-acac-449cf39990ed)
