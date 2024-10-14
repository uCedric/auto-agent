import ollama from 'ollama';
import axios from 'axios';

class llm{
    ollamaInstance;
    model;

    constructor(model) {
        this.ollamaInstance = ollama;
        this.model = model;
        
        console.log('llm instance is created');
    }

    async *inference({prompt}){
        const message  = {role: 'user', content: prompt};

        const result = await this.ollamaInstance.chat({model: this.model, messages: [message], stream: true});

        for await (const part of result) {      
            yield part.message.content;
        }
    }

    static async init(model){
        try {
            if(!await llm.isConnected())throw new Error('Ollama is not connected');
            if(!await llm.isModelExist(model))throw new Error('llm model does not exist');

            return new llm(model); 
        } catch (error) {
            console.log(error.message);
        }
    }

    static async isConnected(){
        return (await axios.get('http://127.0.0.1:11434/')).status === 200;
    }

    static async isModelExist(model){
        const {models: modelList} = await ollama.list();

        for(let modelItem of modelList){
            if(modelItem.name === model)return true;
        }

        return false;
    }
}
 
export default class llmSingleton{
    static async getInstance(model){
        if(llmSingleton.instance)console.log('llmSingleton instance is serving');

        if(!llmSingleton.instance){
            llmSingleton.instance = await llm.init(model);
        }

        return llmSingleton.instance; 
    }
}

export const initializeLlm = async (model) => await llmSingleton.getInstance(model);
// import {fileURLToPath} from "url";
// import path from "path"; 
// import {LlamaModel, LlamaContext, LlamaChatSession} from "node-llama-cpp";

// class llm {
//     session;

//     constructor(){
//         if(!llm.instance){
//             // model nam
//             const MODEL_NAME = "mistral-7b-instruct-v0.2.Q5_K_M.gguf";
//             //const MODEL_NAME = "llama-2-7b.Q5_K_M.gguf";

//             // get the models directory
//             const __filename = fileURLToPath(import.meta.url);
//             const __dirname = path.dirname(__filename);
//             const modelsDirectory = path.join(__dirname, "./models");
//             const modelsPath = path.join(modelsDirectory, MODEL_NAME);
            
//             const model = new LlamaModel({
//                 modelPath : modelsPath,
//                 gpuLayers: 64
//             });

//             const context = new LlamaContext({model});
//             this.session = new LlamaChatSession({context});
//         }
//     }

//     async inference(prompt){
        
//         const startTime = Date.now();
//         const warmupResponse = await this.session.prompt(prompt);
//         const endTime = Date.now();

//         return {
//             response: warmupResponse,
//             time: endTime - startTime
//         };
//     }
// }

// export default class llmSingleton {
//     static getInstance(){
//         if(!llmSingleton.instance){
//             llmSingleton.instance = new llm();
//             console.log("llmSingleton instance is created");
//         }

//         return llmSingleton.instance;
//     }
// }

// export const initializeLlm = () => llmSingleton.getInstance();