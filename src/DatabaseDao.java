/**
 * Created by Huppert on 2018/5/27.
 */
public class DatabaseDao {  /**
 * 插入对象到xml中
 * @param form
 */
public void addUser(User form) {
        /*
         * 1. 得到Document
         * 2. 得到root元素
         * 3. 要把User对象转换成Element元素
         * 4. 把user元素插入到root元素中
         * 5. 回写document
         */
    try {
            /*
             * 1. 得到Docuembnt
             */
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 调用读方法，得到Document
        Document doc = reader.read("F:/users.xml");

            /*
             * 2. 得到根元素
             */
        Element root = doc.getRootElement();
            /*
             * 3. 完成添加元素，并返回添加的元素！
             * 向root中添加一个名为user的元素！并返回这个元素
             */
        Element userElement = root.addElement("user");
        // 设置userElement的属性！
        userElement.addAttribute("username", form.getUsername());
        userElement.addAttribute("password", form.getPassword());
        userElement.addAttribute("age", String.valueOf(form.getAge()));
        userElement.addAttribute("gender", form.getGender());

            /*
             * 回写
             * 注意：创建的users.xml需要使用工具修改成UTF-8编码！
             * Editplus：标记列--> 重新载入为 --> UTF-8
             */

        // 创建目标输出流，它需要与xml文件绑定
        Writer out = new PrintWriter("F:/users.xml", "UTF-8");
        // 创建格式化器
        OutputFormat format = new OutputFormat("\t", true);
        format.setTrimText(true);//先干掉原来的空白(\t和换行和空格)！

        // 创建XMLWrtier
        XMLWriter writer = new XMLWriter(out, format);

        // 调用它的写方法，把document对象写到out流中。
        writer.write(doc);

        // 关闭流
        out.close();
        writer.close();

    } catch(Exception e) {
        // 把编译异常转换成运行异常！
        throw new RuntimeException(e);
    }
}

    /**
     * 按用户名进行查询
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        /*
         * 1. 得到Docuemnt
         * 2. 给出xpath表达式
         * 3. 调用docuemnt的方法进行xpath查询，得到Element
         * 4. 把Element转换成User对象，返回！
         */
        try {
            /*
             * 1. 得到Docuembnt
             */
            // 创建解析器
            SAXReader reader = new SAXReader();
            // 调用读方法，得到Document
            Document doc = reader.read("F:/users.xml");

            /*
             * 2. 准备xpath
             *  //开头表示没有深的限制，可以在文档查询子元素、子元素的子元素！
             *  []中放的叫谓语，其实就是查询条件
             *  @username表示username属性，限定其必须等于方法参数username
             */
            String xpath = "//user[@username='" + username + "']";
            /*
             * 3. 调用document方法完成查询
             */
            Element userEle = (Element)doc.selectSingleNode(xpath);
            if(userEle == null) {
                return null;
            }
            /*
             * 4. 把元素转换成User类的对象，然后返回
             */
            User user = new User();
            // 获取元素的username属性值，赋给对象的username属性
            user.setUsername(userEle.attributeValue("username"));
            user.setPassword(userEle.attributeValue("password"));
            user.setAge(Integer.parseInt(userEle.attributeValue("age")));
            user.setGender(userEle.attributeValue("gender"));

            return user;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
