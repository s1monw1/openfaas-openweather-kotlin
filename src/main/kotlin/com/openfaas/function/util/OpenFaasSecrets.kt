package com.openfaas.function.util

import java.io.BufferedReader
import java.io.FileInputStream
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Implementation based on OpenFaas and its secrets support.
 *
 * Read here: https://docs.openfaas.com/reference/secrets/#use-the-secret-in-your-function
 */
class OpenFaasSecrets(private val baseDirectory: Path = Paths.get("/var/openfaas/secrets")) {

    private fun createSecretPath(secretName: String): String =
        baseDirectory.resolve(secretName).toAbsolutePath().toString()

    fun getSecretValue(secretName: String): String {
        val secretPath = createSecretPath(secretName)
        return try {
            FileInputStream(secretPath).bufferedReader().use(BufferedReader::readLine)
        } catch (ex: Exception) {
            logger { "Cannot read from $secretPath: $ex" }
            throw IllegalArgumentException("Cannot find secret $secretName".also { logger { it } })
        }
    }
}
