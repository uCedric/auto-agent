import {fileURLToPath} from "url";
import path from "path";
import {LlamaModel, LlamaContext, LlamaChatSession} from "node-llama-cpp";

class llm {
    session;

    constructor(){
        if(!llm.instance){
            // model nam
            const MODEL_NAME = "mistral-7b-instruct-v0.2.Q5_K_M.gguf";
            //const MODEL_NAME = "llama-2-7b.Q5_K_M.gguf";

            // get the models directory
            const __filename = fileURLToPath(import.meta.url);
            const __dirname = path.dirname(__filename);
            const modelsDirectory = path.join(__dirname, "../../utils");
            const modelsPath = path.join(modelsDirectory, MODEL_NAME);
            
            const model = new LlamaModel({
                modelPath : modelsPath,
                gpuLayers: 64
            });

            const context = new LlamaContext({model});
            this.session = new LlamaChatSession({context});
        }
    }

    async inference(prompt){
        
        const startTime = Date.now();
        const warmupResponse = await this.session.prompt(prompt);
        const endTime = Date.now();

        return {
            response: warmupResponse,
            time: endTime - startTime
        };
    }
}

export default class llmSingleton {
    static getInstance(){
        if(!llmSingleton.instance){
            llmSingleton.instance = new llm();
            console.log("llmSingleton instance is created");
        }

        return llmSingleton.instance;
    }
}

export const initializeLlm = () => llmSingleton.getInstance();