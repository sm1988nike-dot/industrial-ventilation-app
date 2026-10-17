# 산업환기 후드 필요환기량 계산기

브라우저에서 `index.html`을 열면 실행됩니다. 백엔드, 로그인, 별도 설치가 필요하지 않습니다.

## 기능
- 부스식 / 외부식(일반 개구, 플랜지 개구, 일반 슬롯, 플랜지 슬롯) / 캐노피식
- 개구부/발생원 **사각형 또는 원형** 선택 (슬롯형은 별도 길이와 폭 사용)
- 사각형 면적 `A = W × H`, 원형 면적 `A = π × d² / 4`
- 캐노피식 발생원 둘레: 사각형 `P = 2(W+H)`, 원형 `P = πd`
- m³/h 및 m³/min 실시간 계산, 결과 복사, CSV, 인쇄

## GitHub Pages 배포
1. GitHub에서 새 공개 저장소 생성
2. `index.html`과 `README.md` 업로드
3. Settings → Pages → Build and deployment → Deploy from a branch → `main` / `(root)` → Save
4. 잠시 후 `https://사용자명.github.io/저장소명/` 주소로 접속

## 참고와 제한
- 후드 종류별 경험식은 ACGIH *Industrial Ventilation: A Manual of Recommended Practice*의 관련 도표에 기반함. 원문 확인: https://law.resource.org/pub/us/cfr/ibr/001/acgih.manual.1998.pdf
- AIHA Plain Opening Flow Rate: https://www.aiha.org/ih-calculator-app/calcs/plain-opening-flow-rate.html
- AIHA Canopy Flow Rate: https://www.aiha.org/ih-calculator-app/calcs/canopy-flow-rate.html
- 제어속도는 현장과 작업 조건에 따라 별도로 선정해야 함. 계산 결과는 실제 설계 승인이나 송풍기 용량 검증을 대체하지 않음.
- 외부식 개구형 공식의 적용성은 개구 형상·종횡비 및 거리 조건에 따라 달라짐. 길쭉한 사각형의 경우 별도 검토 권장.