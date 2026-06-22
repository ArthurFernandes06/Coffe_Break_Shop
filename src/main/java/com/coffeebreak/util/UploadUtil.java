package com.coffeebreak.util;

import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Classe utilitária responsável por salvar arquivos de imagem enviados
 * via formulário (multipart/form-data) no diretório de uploads do servidor.
 *
 * O diretório usado aqui é o mesmo lido pelo ImageServlet para servir
 * as imagens em /uploads/*.
 */
public class UploadUtil {

    public static final String UPLOAD_DIR = "/var/vols/uploads";

    private UploadUtil() {}

    /**
     * Salva o arquivo recebido em um campo <input type="file"> no diretório
     * de uploads, gerando um nome único para evitar sobrescrita de arquivos.
     *
     * @param part o Part recebido via request.getPart("foto")
     * @return o nome do arquivo salvo (para ser persistido no banco), ou
     *         null caso nenhum arquivo tenha sido enviado.
     */
    public static String salvarImagem(Part part) throws IOException {
        if (part == null || part.getSize() == 0) {
            return null;
        }

        String nomeOriginal = part.getSubmittedFileName();
        String extensao = "";
        if (nomeOriginal != null && nomeOriginal.contains(".")) {
            extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));
        }

        String nomeArquivo = UUID.randomUUID() + extensao;

        Path diretorio = Paths.get(UPLOAD_DIR);
        Files.createDirectories(diretorio);

        Path destino = diretorio.resolve(nomeArquivo);
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, destino, StandardCopyOption.REPLACE_EXISTING);
        }

        return nomeArquivo;
    }
}
