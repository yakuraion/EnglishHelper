package pro.yakuraion.englishhelper.vocabulary.ui.addwords

data class AddWordsUiState(
    val word: String = "",
    val isAddButtonLoading: Boolean = false,
    val isWordAlreadyExistError: Boolean = false
) {

    val isAddButtonEnabled: Boolean
        get() = word.length > 1

    val alreadyExistWord: String?
        get() = if (isWordAlreadyExistError) word else null
}
