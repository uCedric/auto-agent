const promptQuery = async (prompt) => {
    const promptRes = await global.llmInstance.inference(prompt);

    return promptRes;
};

export default { promptQuery };