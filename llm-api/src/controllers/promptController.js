import promptService from "../services/prompt.service.js";

const promptQuery = async (req, res) => {
    const { prompt } = req.body;

    const promptRes = await promptService.promptQuery(prompt);

    res.success(promptRes);
};

export default { promptQuery };
