package com.mega.megasenachecker;

import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

/**
 * Entrypoint mantido na raiz do pacote para compatibilidade com a configuração do AWS Lambda.
 * Handler configurado na AWS: com.mega.megasenachecker.LambdaHandler
 */
public class LambdaHandler extends FunctionInvoker {
}

