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
                  SELECT * FROM schema.table 
                  wherfe name = ? or age = ?
              titleName: a test                    # the title for this job
              comment: comment for test.                # describe this job
              key-value: >                           
                   name:5,age:6                   
                                                      
          testJob02:                                 # example2
              sqlValue: > 
                  SELECT * FROM schema.table02 
                  where phone = ?
              titleName: test 02
              comment: |
                  comment for test 02.
                  It's my test 02.
              key-value: >
                   phone:00-0000-0000
  ```

   key-value:  use the parameter in replace sqlValue '?', the pattern is
               **"value 1 Description":"default value 1", "value 2 Description":"default value 2"**
# run
run main method and go to http://localhost:9999/index.html 

![index](https://cloud.githubusercontent.com/assets/7590449/16683105/b3beba16-4530-11e6-82c0-5d2dce5a1a00.png)

you can click  execute link to go to your SQL function web page
![job](https://cloud.githubusercontent.com/assets/7590449/16683153/e548374c-4530-11e6-82fe-9b7eeba8cb2d.png)
![showSql](https://cloud.githubusercontent.com/assets/7590449/16683190/17fffdaa-4531-11e6-9bed-40a1abb4f1f3.png)
