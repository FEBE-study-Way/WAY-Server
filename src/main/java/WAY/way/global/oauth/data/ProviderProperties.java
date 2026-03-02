package WAY.way.global.oauth.data;

/**
 * OAuth 제공자 설정 프로퍼티 DTO.
 *
 * @param clientId     OAuth 클라이언트 ID
 * @param clientSecret OAuth 클라이언트 시크릿
 * @param tokenUri     Access Token 교환 엔드포인트 URI
 * @param userInfoUri  사용자 정보 조회 엔드포인트 URI
 * @param redirectUri  OAuth 인가 후 리다이렉트 URI
 */
public record ProviderProperties(
        String clientId,
        String clientSecret,
        String tokenUri,
        String userInfoUri,
        String redirectUri
) {
}
