import {fileURLToPath} from "url";
import path from "path";
import {LlamaModel, LlamaContext, LlamaChatSession} from "node-llama-cpp";

// model name
const MODEL_NAME = "mistral-7b-instruct-v0.2.Q5_K_M.gguf";
//const MODEL_NAME = "llama-2-7b.Q5_K_M.gguf";

// get the models directory
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const modelsDirectory = path.join(__dirname, "./utils");
const modelsPath = path.join(modelsDirectory, MODEL_NAME);


const model = new LlamaModel({
    modelPath : modelsPath
});

const context = new LlamaContext({model});
const session = new LlamaChatSession({context});

const warmupPrompt = "teach me about how a embedding model works?";

const startTime = Date.now();
const warmupResponse = await session.prompt(warmupPrompt);
const endTime = Date.now();

console.log("Warmup response: ", warmupResponse);