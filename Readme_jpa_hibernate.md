## JDBC to Spring JDBC to JPA to Spring Data JPA
- **Spring JDBC**
  > - Write a lot of SQL queries (delete from todo where id=?)
  > - BUT lesser Java code
    ```agsl
    @Repository
    public class CourseJdbcRepository {
    
        private static final String INSERT_QUERY = """
                INSERT INTO COURSE (ID, NAME, author) VALUES (?, ?, ?);
                """;
    
        private static final String DELETE_QUERY = """
                DELETE FROM COURSE WHERE ID = ?;
                """;
    
        private static final String SELECT_QUERY = """
                SELECT * FROM COURSE;
                """;
    
        private final JdbcTemplate jdbcTemplate;
    
        @Autowired
        public CourseJdbcRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }
    
        public void insert(Course course) {
            jdbcTemplate.update(INSERT_QUERY, course.getId(), 
                course.getName(), 
                course.getAuthor());
        }
    
        public void deleteById(Long id) {
            jdbcTemplate.update(DELETE_QUERY, id);
        }
    
        public List<Course> selectAll() {
            return jdbcTemplate.query(SELECT_QUERY, (resultSet, i) -> 
                      new Course(resultSet.getLong("id"), 
                                resultSet.getString("name"), 
                                resultSet.getString("author")
            ));
        }
    }
    ```
- **Spring JPA**
  >   - Do NOT worry about queries
  >   - Just Map Entities to Tables! 
  ``` agsl
      @Repository
      @Transactional
      public class CourseJpaRepository {
    
          @PersistenceContext
          private final EntityManager entityManager;
    
          @Autowired
          public CourseJpaRepository(EntityManager entityManager) {
              this.entityManager = entityManager;
          }
    
          public void insert(Course course) {
              entityManager.merge(course);
          }
    
          public Optional<Course> findById(Long id) {
              return Optional.ofNullable(entityManager.find(Course.class, id));
          }
    
          public void deleteById(Long id) {
              Course course = findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
              entityManager.remove(course);
          }
      } 
    ```
- **Spring Data JPA**
  > - Let's make JPA even more simple!
  > - I will take care of everything!
  > - Just extend my interface!
  ``` agsl
      public interface CourseSpringDataJpaRepository extends JpaRepository<Course, Long> {
      }
      
      public class service1 {
          @Autowired
          private CourseSpringDataJpaRepository courseSpringDataJpaRepository;
          courseSpringDataJpaRepository.saveAll(
                List.of(
                        new Course(1L, "Spring Boot", "Ranga"),
                        new Course(2L, "Learn Azure", "Ranga1"),
                        new Course(3L, "Spring AWS", "Ranga2")
                )
          );

         courseSpringDataJpaRepository.findAll().forEach(System.out::println);
         courseSpringDataJpaRepository.findById(1L).ifPresent(System.out::println);
         courseSpringDataJpaRepository.deleteById(2L);
      }
    ```
***
## Hibernate vs JPA
- JPA defines the specification. It is an API.
  > - How do you define entities?
  > - How do you map attributes?
  > - Who manages the entities?
- Hibernate is one of the popular implementations of JPA
- Using Hibernate directly would result in a lock in to Hibernate
  > - There are other JPA implementations (Toplink, for example)