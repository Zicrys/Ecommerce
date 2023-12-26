package controllers;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import models.BaseConnection;
 

@WebServlet("/AddPhoto")
public class AddPhoto extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final String UPLOAD_THUMBS_DIRECTORY = "C:/Users/admin/eclipse-workspace/shop/WebContent/uploads/thumbs/"; // указываем путь к папкам, в которые будем загружать фото 
	private final String UPLOAD_REAL_SIZE_DIRECTORY = "C:/Users/admin/eclipse-workspace/shop/WebContent/uploads/real/";
	private final String UPLOAD_LARGE_SIZE_DIRECTORY = "C:/Users/admin/eclipse-workspace/shop/WebContent/uploads/large/";
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        if(BaseConnection.UserExist(request, response)){
        	HttpSession session = request.getSession();
			if (session.getAttribute("Username").equals("admin")){
        	 request.getRequestDispatcher("views/addPhoto.jsp").forward(request,response);
        	 
			}
			else {
				 request.getRequestDispatcher("views/error.jsp").forward(request,response);
			}
        }
        else {
			 request.getRequestDispatcher("views/error.jsp").forward(request,response);
        } 
		
	}
	
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
    	HttpSession session = request.getSession();
        int IdGoodPhoto=(int)session.getAttribute("IdGoodPhoto"); // при нажатии на ссылку получаем id товара и через сессии передаем его сюда.
        if(ServletFileUpload.isMultipartContent(request)){ // далее идет процесс получения переднного изображния.
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                                         new DiskFileItemFactory()).parseRequest(request);
               
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        String name = new File(item.getName()).getName();
                        int size1 = 100;
                        int size2 = 250;
                        int size3 = 650;
                        InputStream imageStream = item.getInputStream();
                        BufferedImage image = javax.imageio.ImageIO.read(imageStream); 
                        BufferedImage newImage1 = scaleImage(image, size1); // перед загрузкой на сервер приводим изображение к стандартному размеру 
                        BufferedImage newImage2 = scaleImage(image, size2);
                        BufferedImage newImage3 = scaleImage(image, size3);
                        File file1 = new File(UPLOAD_THUMBS_DIRECTORY, name); // Здесь записываем полноразмерные изображения
                        ImageIO.write(newImage1, "JPG", file1);
                        File file2 = new File(UPLOAD_REAL_SIZE_DIRECTORY, name); // Здесь записываем уменьшенные копии. 
                        ImageIO.write(newImage2, "JPG", file2); //После загрузки изображений в папку необходимо вручную обновить папку проекта, иначе eclipse не видит новые фото в папке uploads. 
                        File file3 = new File(UPLOAD_LARGE_SIZE_DIRECTORY, name);
                        ImageIO.write(newImage3, "JPG", file3);
                        BaseConnection.insertPhoto(IdGoodPhoto, name); // присваеваем каждому товару соответствующее имя изображения в базе данных
                    }
                }
            
               //File uploaded successfully
               request.setAttribute("message", "Изображение изменено!");
            } catch (Exception ex) {
               request.setAttribute("message", "Загрузка файла прервана по причине: " + ex);
            }          
          
        }else{
            request.setAttribute("message",
                                 "На этой странице возмона только загрузка файлов!");
        }
     
        request.getRequestDispatcher("views/addPhoto.jsp").forward(request, response);
      
    }
    
    private BufferedImage scaleImage(BufferedImage bufferedImage, int size) { // Функция, изменяющая размеры изображения.
        double boundSize = size;
           int origWidth = bufferedImage.getWidth();
           int origHeight = bufferedImage.getHeight();
           double scale;
           if (origHeight > origWidth) // проверяем, если высота исходного изображения больше ширины
               scale = boundSize / origHeight;
           else // если ширина больше высоты
               scale = boundSize / origWidth;
           if (scale > 1.0) // если получившаяся константа больше 1, т.е исходное изображение меньше требуемого, возвращаем исходное изображение.
               return (bufferedImage);
           int scaledWidth = (int) (scale * origWidth); // иначе умножаем стороны на константу для изенения размера
           int scaledHeight = (int) (scale * origHeight);
           Image scaledImage = bufferedImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
           BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
           Graphics2D g = scaledBI.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
           g.drawImage(scaledImage, 0, 0, null);
           g.dispose();
           return (scaledBI); // возврщаем измененное изображение
   }
   
}