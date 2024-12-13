# Conversão de Arquivos PDF <-> Word com Spring Boot

Este projeto fornece uma API REST em Spring Boot para conversão de documentos entre PDF e Word. A API permite que você converta arquivos PDF para documentos Word (.docx) e vice-versa, preservando o nome original dos arquivos. A conversão é realizada utilizando as bibliotecas Apache POI e iText, além do LibreOffice em modo headless para conversão de Word para PDF, garantindo a preservação do layout.

## Funcionalidades

- **PDF para Word**: Converte um arquivo PDF para um arquivo Word (.docx) mantendo o conteúdo textual.
- **Word para PDF**: Converte um arquivo Word (.docx) para um arquivo PDF, preservando o layout, fontes, imagens e tabelas.

## Tecnologias Usadas

- **Spring Boot**: Framework Java para criação da API REST.
- **Apache POI**: Biblioteca para manipulação de documentos Microsoft Office, usada para leitura de arquivos Word.
- **iText**: Biblioteca para manipulação de documentos PDF, usada para criação de arquivos PDF a partir de Word.
- **LibreOffice**: Usado para conversão de Word para PDF mantendo o layout original, executado em modo headless.
