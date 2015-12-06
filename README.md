# QE

it's template to develop new simple in web.

# techical stack

## back-end

 * spring boot
     * spring mvc
     * spring jdbcTemplate
 
## front-end 

* angularjs ([link](https://angularjs.org/))
* sweetalert2 ([link](https://github.com/limonte/sweetalert2))
* bootstrap ([link](http://getbootstrap.com/))
* HoldOn.js ([link](https://sdkcarlos.github.io/sites/holdon.html))

# usage

## config database:

* application.yml
  ``` yml
  datasource:
    execute:
        url: jdbc:mysql://127.0.0.1:3306/schema  # your db url
        username: myUser                         # your db user name
        password: pass                           # your db user password
        driver-class-name: com.mysql.jdbc.Driver # driver class
  ```
  
* develop sql executor function

  application.yml
  ``` yml
  SqlQueryFunction:
      sql-configs:
          testJob:                                   # example1: the key is job id
              sqlValue: >                            
                  SELECT * FROM ebookdba.aaatable 
                  wherfe aaa = ? or bbb = ?
              titleName: 一個測試                    # the title for this job
              comment: 一個測試的註解                # describe this job
              key-value: >                           
                   aaa值:5,bbb值:6                   
                                                      
          testJob02:                                 # example2
              sqlValue: > 
                  SELECT * FROM ebookdba.aaatable 
                  where aaa = ?
              titleName: 一個測試02
              comment: |
                  一個測試的註解02
                  換行摟
              key-value: >
                   aaa值:5
  ```

   key-value:  use the parameter in replace sqlValue '?', the pattern is
               **"value 1 Description":"default value 1", "value 2 Description":"default value 2"**
# run
run main method and go to http://localhost:9999/index.html 

![index](https://cloud.githubusercontent.com/assets/7590449/11613396/955b4f10-9c59-11e5-9377-93f25748cf5c.PNG)

you can click  execute link to go to your SQL function web page
![job](https://cloud.githubusercontent.com/assets/7590449/11613406/0023767e-9c5a-11e5-9062-22bac93b1160.PNG)
