package personaltaste.entity.model.taste

/**
 * Taste 상태 코드
 * - 현재는 {@link UserStatus} 와 차이가 없지만 별도의 상태로 확장될 가능성이 있다고 판단하여 별도의 클래스로 분리
 * - 만약, 별도의 상태가 없을 것 같다면 공통된 Status Class 로 병합해도 괜찮을듯?
 *
 * @author seungmin
 */
enum class TasteStatus {
    ACTIVE,
    DELETE
}
