# LiteGuard Model Training & Compression Guide

A comprehensive guide for training and compressing medical language models using the LiteGuard multi-stage fine-tuning pipeline for both Phi-1.5 and Phi-2 base models.

**Main Repository**: [https://github.com/TSTS09/LiteGuard/tree/main](https://github.com/TSTS09/LiteGuard/tree/main)

## Overview

LiteGuard implements a novel multi-stage fine-tuning approach that transforms general-purpose language models into specialized medical assistants optimized for edge deployment. The pipeline consists of three sequential stages:

1. **Domain Adaptation** - Medical knowledge acquisition using QLoRA
2. **Instruction Tuning** - Teaching instruction-following behavior 
3. **Safety Alignment** - Ensuring ethical medical guidance via DPO

## Prerequisites

### Hardware Requirements
- **GPU**: NVIDIA A100 (40GB VRAM) recommended, RTX 4070 (8GB) minimum
- **RAM**: 32GB+ system memory
- **Storage**: 250GB+ free space for datasets and models

### Software Requirements
```bash
torch>=2.0.0
transformers>=4.35.0
peft>=0.7.0
bitsandbytes>=0.41.0
trl>=0.7.0
datasets>=2.14.0
```

## Supported Base Models

### Phi-1.5 (1.3B Parameters)
- **Context Length**: 2048 tokens
- **Memory Usage**: ~6GB during training
- **Training Time**: ~6 hours on A100
- **Final Model Size**: ~1GB quantized

### Phi-2 (2.7B Parameters) 
- **Context Length**: 2048 tokens  
- **Memory Usage**: ~12GB during training
- **Training Time**: ~10 hours on A100
- **Final Model Size**: ~2GB quantized

## Training Pipeline

### Stage 1: Domain Adaptation

Transforms the base model to understand medical terminology using QLoRA with 4-bit quantization.

**Key Parameters:**
- LoRA Rank: 32 (higher capacity for medical concepts)
- Alpha: 32 (balanced scaling)
- Target Modules: All attention and FFN layers
- Learning Rate: 2e-4
- Training Steps: 1000

**Dataset**: 149,073 medical texts from licensing exams

```python
# QLoRA Configuration
peft_config = LoraConfig(
    r=32,
    lora_alpha=32,
    target_modules=["q_proj", "k_proj", "v_proj", "o_proj", 
                   "gate_proj", "up_proj", "down_proj"],
    lora_dropout=0.0,
    bias="none"
)
```

**Expected Results:**
- ROUGE-1: +3.64% improvement
- ROUGE-2: +7.95% improvement  
- BLEU Score: 0.9042

### Stage 2: Instruction Tuning

Teaches the domain-adapted model to follow medical instructions using Supervised Fine-Tuning.

**Key Parameters:**
- Learning Rate: 1e-5 (conservative to preserve knowledge)
- Training Steps: 1000
- Batch Size: 4 with gradient accumulation

**Dataset**: 388,828 medical Q&A instruction pairs

**Expected Results:**
- ROUGE improvements: 57%-71% over base model
- BLEU Score: 0.3189
- Structured, focused responses

### Stage 3: Safety Alignment

Applies Direct Preference Optimization to ensure ethical medical guidance.

**Key Parameters:**
- Beta: 0.1 (balanced preference learning)
- Batch Size: 2 (DPO memory constraints)
- Training Steps: 200

**Dataset**: 147,530 preference pairs (safe vs unsafe responses)

**Expected Results:**
- Refusal Rate: 47.62% for harmful prompts
- BLEU Score: 0.4724 (+51.6% from instruction tuning)
- Consistent, professional outputs

## Model Compression & Conversion

### 1. Merge LoRA Adapters
```python
# Merge trained adapters with base model
merged_model = model.merge_and_unload()
```

### 2. Quantization (8-bit)
```python
import ai_edge_torch

# Convert to TensorFlow Lite with quantization
edge_model = ai_edge_torch.convert(
    merged_model,
    prefill_seq_len=1024,
    kv_cache_max_len=1280,
    quantize=True  # 8-bit quantization
)
```

### 3. Bundle for Deployment
```python
# Create MediaPipe .task file
bundler = mediapipe_bundle.create_task_bundle(
    model_path="model.tflite",
    tokenizer_path="tokenizer.model"
)
```

## Performance Metrics

### Phi-1.5 Results
| Metric | Base | Domain | Instruction | Final |
|--------|------|--------|-------------|-------|
| ROUGE-1 | 0.285 | 0.321 | 0.412 | 0.398 |
| ROUGE-2 | 0.156 | 0.235 | 0.278 | 0.265 |
| BLEU | 0.12 | 0.89 | 0.31 | 0.45 |

### Phi-2 Results  
| Metric | Base | Domain | Instruction | Final |
|--------|------|--------|-------------|-------|
| ROUGE-1 | 0.298 | 0.335 | 0.425 | 0.417 |
| ROUGE-2 | 0.168 | 0.248 | 0.286 | 0.271 |
| BLEU | 0.15 | 0.90 | 0.32 | 0.47 |

## Quick Start

### 1. Setup Environment
```bash
git clone https://github.com/TSTS09/LiteGuard.git
cd LiteGuard/training
pip install -r requirements.txt
```

### 2. Prepare Data
```bash
python prepare_datasets.py --model_type phi2
```

### 3. Run Training Pipeline
```bash
# Stage 1: Domain Adaptation
python train_domain.py --model microsoft/phi-2 --rank 32

# Stage 2: Instruction Tuning  
python train_instruction.py --checkpoint domain_checkpoint

# Stage 3: Safety Alignment
python train_safety.py --checkpoint instruction_checkpoint
```

### 4. Compress & Convert
```bash
python compress_model.py --input final_checkpoint --output liteguard_phi2.task
```

## Memory Optimization Tips

### For Limited GPU Memory (8GB RTX 4070):
- Use gradient checkpointing
- Reduce batch size to 1-2
- Enable gradient accumulation
- Use 4-bit quantization during training

### For Stable Training:
- Monitor validation loss every 100 steps
- Save checkpoints frequently  
- Use mixed precision (fp16/bf16)
- Implement early stopping

## Model Selection Guide

**Choose Phi-1.5 if:**
- Limited computational resources
- Faster inference required
- Basic medical Q&A sufficient
- <1GB model size needed

**Choose Phi-2 if:**
- Better medical reasoning required
- Complex clinical scenarios
- More comprehensive knowledge needed
- 2GB model size acceptable

## Troubleshooting

### Common Issues:
- **CUDA OOM**: Reduce batch size, enable gradient checkpointing
- **Loss not decreasing**: Check learning rate, verify data preprocessing
- **Quantization errors**: Ensure model architecture compatibility
- **Conversion failures**: Verify ai-edge-torch version compatibility

### Training Monitoring:
```python
# Log key metrics
wandb.log({
    "train_loss": loss.item(),
    "learning_rate": scheduler.get_last_lr()[0],
    "gpu_memory": torch.cuda.memory_allocated() / 1e9
})
```

## Deployment

After training and compression, deploy using the [LiteGuard Android application](../Model%20deployment/) which supports:
- Offline inference
- Multiple model formats
- Secure authentication
- Context management

---

**Note**: Training requires significant computational resources. Consider using cloud platforms (Google Colab Pro, AWS) for optimal results. Always validate model outputs before clinical use.
