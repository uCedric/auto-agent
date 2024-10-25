import llmConn from '../utils/llmInit.js';
async function* promptQuery({prompt, relatedInfos}) {
    const llmInstance = await llmConn.getInstance({model: 'llama3.2:3b'});
    const promptRes = llmInstance.inference({prompt, relatedInfos});

    for await (const part of promptRes) {
        process.stdout.write(part);
        yield part;
    }
}

export default { promptQuery };