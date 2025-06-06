package com.google.mediapipe.examples.llminference


import com.google.mediapipe.tasks.genai.llminference.LlmInference.Backend

// NB: Make sure the filename is *unique* per model you use!
// Weight caching is currently based on filename alone.
enum class Model(
    val path: String,
    val url: String,
    val licenseUrl: String,
    val needsAuth: Boolean,
    val preferredBackend: Backend?,
    val uiState: UiState,
    val temperature: Float,
    val topK: Int,
    val topP: Float,
) {
    LiteGuard_500MB(
        path = "/data/local/tmp/gemma3-1b-it-int4.task",
        url = "https://huggingface.co/litert-community/Gemma3-1B-IT/resolve/main/gemma3-1b-it-int4.task",
        licenseUrl = "https://huggingface.co/litert-community/Gemma3-1B-IT",
        needsAuth = true,
        preferredBackend = Backend.CPU,
        uiState = GenericUiState(),
        temperature = 1f,
        topK = 64,
        topP = 0.95f
    ),
    LiteGuard_1GB(
        path = "/data/local/tmp/llm/deepseek3k_q8_ekv1280.task",
        url = "https://huggingface.co/litert-community/DeepSeek-R1-Distill-Qwen-1.5B/resolve/main/deepseek_q8_ekv1280.task",
        licenseUrl = "",
        needsAuth = false,
        preferredBackend = null,
        uiState = DeepSeekUiState(),
        temperature = 0.6f,
        topK = 40,
        topP = 0.7f
    ),
    LiteGuard_2GB(
        path = "/data/local/tmp/llm/LiteGuard_merged_phi2.task",
        url = "https://huggingface.co/T-tchi0/LiteGuard-merged-TFLITE/resolve/main/LiteGuard_merged.task",
        licenseUrl = "",
        needsAuth = false,
        preferredBackend = null,
        uiState = GenericUiState(),
        temperature = 0.0f,
        topK = 40,
        topP = 1.0f
    ),
    LiteGuard_3GB(
        path = "/data/local/tmp/llm/phi4_q8_ekv1280.task",
        url = "https://huggingface.co/litert-community/Phi-4-mini-instruct/resolve/main/phi4_q8_ekv1280.task",
        licenseUrl = "",
        needsAuth = false,
        preferredBackend = null,
        uiState = GenericUiState(),
        temperature = 0.0f,
        topK = 40,
        topP = 1.0f
    ),
}
