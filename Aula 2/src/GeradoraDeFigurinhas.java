import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.awt.font.TextLayout;
import java.awt.Shape;
import java.awt.FontMetrics;
import java.awt.BasicStroke;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {
    
    public void cria(InputStream inputStream, String nomeArquivo, String texto) throws Exception {

        // leitura da imagem
        //InputStream inputStream = new FileInputStream(new File("entrada/filme-maior.jpg"));
        // InputStream inputStream = new URL("https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@.jpg").openStream();
        BufferedImage imagemLink = ImageIO.read(inputStream);
        Image imagemOriginal = imagemLink.getScaledInstance(600,900, Image.SCALE_DEFAULT);

        // cria nova imagem em memória com transparência e com tamanho novo

        int largura = 600;
        int altura = 900;
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // copiar a imagem original para nova imagem (em memória)

        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        //configurar a fonte
        var fonte = new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, 120);

        TextLayout layoutFont = new TextLayout(texto, fonte, graphics.getFontRenderContext());
        Shape shape = layoutFont.getOutline(null);
        graphics.setColor(Color.BLACK);
        graphics.setFont(fonte);
        FontMetrics tamanhoFonte = graphics.getFontMetrics(fonte);

        // escrever uma frase na nova imagem
        int yFonte = novaAltura - (tamanhoFonte.getAscent());
        int xFonte = (largura - tamanhoFonte.stringWidth(texto));

        graphics.translate(xFonte, yFonte);
        graphics.setStroke(new BasicStroke(5));
        graphics.draw(shape);

        graphics.setColor(Color.YELLOW);
        graphics.fill(shape);

        // escrever a nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File("saida/" + nomeArquivo));

    }
}
