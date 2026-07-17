## Plan: Portfolio-Ready Workout Tracker API

Goal: turn the current starter API into a backend that looks credible in a portfolio by adding a real auth layer, a useful workout domain, quality features, and presentation polish. Each step below includes where to look in this repo and which external references are most useful while implementing it.

**Steps**
1. Stabilize the foundation and API shape, *depends on nothing*.
   - What to implement: tighten request validation, stop returning persistence entities directly, standardize error responses, and make health/security behavior explicit.
   - Repo resources:
     - [src/main/java/com/edmilson/workouttracker/controller/AuthController.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/controller/AuthController.java) for the current auth surface.
     - [src/main/java/com/edmilson/workouttracker/controller/HealthController.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/controller/HealthController.java) for readiness/liveness behavior.
     - [src/main/java/com/edmilson/workouttracker/config/SecurityConfig.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/config/SecurityConfig.java) for route policy.
     - [src/main/java/com/edmilson/workouttracker/dto/RegisterRequest.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/dto/RegisterRequest.java) and [src/main/java/com/edmilson/workouttracker/dto/RegisterResponse.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/dto/RegisterResponse.java) for request/response shape.
     - [src/main/java/com/edmilson/workouttracker/entity/User.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/entity/User.java) for fields that should never leak out of controllers.
   - External references:
     - Spring Boot validation docs for Jakarta Bean Validation annotations and request binding.
     - Spring Boot error handling docs for ProblemDetail or @RestControllerAdvice patterns.
     - Spring Security reference docs for SecurityFilterChain and route authorization.

2. Build a real authentication and user identity layer, *depends on step 1*.
   - What to implement: registration, login, password hashing, token-based auth, and a protected profile endpoint.
   - Repo resources:
     - [src/main/java/com/edmilson/workouttracker/controller/AuthController.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/controller/AuthController.java) and [src/main/java/com/edmilson/workouttracker/service/UserService.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/service/UserService.java) for the main auth flow.
     - [src/main/java/com/edmilson/workouttracker/entity/User.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/entity/User.java) and [src/main/java/com/edmilson/workouttracker/repository/UserRepository.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/repository/UserRepository.java) for user lookup and persistence rules.
     - [src/main/java/com/edmilson/workouttracker/config/PasswordConfig.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/config/PasswordConfig.java) for password encoder setup.
   - External references:
     - Spring Security password encoding docs.
     - JWT library documentation if you choose JWT for auth tokens.
     - Spring Security authentication docs for user details, filters, or bearer token support.
   - Implementation note: use response DTOs for auth instead of returning User directly.

3. Introduce the workout domain, *depends on step 2*.
   - What to implement: workouts, exercises, workout sessions, and sets/reps/weights, with ownership tied to the authenticated user.
   - Repo resources:
     - [src/main/java/com/edmilson/workouttracker/controller](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/controller) for controller conventions.
     - [src/main/java/com/edmilson/workouttracker/dto](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/dto) for request and response patterns.
     - [src/main/java/com/edmilson/workouttracker/entity/User.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/entity/User.java) as the parent aggregate to relate new workout entities to.
     - [src/main/java/com/edmilson/workouttracker/repository/UserRepository.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/repository/UserRepository.java) as the repository style to mirror for new entities.
   - External references:
     - Spring Data JPA docs for entity relationships, cascades, and repository patterns.
     - Hibernate/JPA docs for modeling one-to-many and many-to-many relationships.
   - Portfolio-friendly feature candidates: template-based workouts, session logging, and cloning a previous workout plan.

4. Add portfolio-grade product features, *depends on step 3*.
   - What to implement: progress summaries, personal records, volume totals, recent activity, and optional body metric or goal tracking.
   - Repo resources:
     - Existing workout domain entities from step 3 as the source of truth for summaries.
     - [src/main/java/com/edmilson/workouttracker/controller/HealthController.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/controller/HealthController.java) if you want to expand to readiness or operational checks only.
   - External references:
     - Spring Data JPA paging and sorting docs for filterable list endpoints.
     - Spring MVC docs for query parameter binding and pageable requests.
     - Domain design references for aggregate summary endpoints if you want to shape the API like a real product.

5. Improve API quality signals, *parallel with steps 2-4 where appropriate*.
   - What to implement: tests, global errors, API docs, and a migration strategy.
   - Repo resources:
     - [src/test/java/com/edmilson/workouttracker/WorkoutTrackerApplicationTests.java](/workspaces/workout-tracker-api/src/test/java/com/edmilson/workouttracker/WorkoutTrackerApplicationTests.java) as the current baseline to replace with meaningful coverage.
     - [pom.xml](/workspaces/workout-tracker-api/pom.xml) for test, docs, and migration dependencies.
     - [src/main/resources/application.properties](/workspaces/workout-tracker-api/src/main/resources/application.properties) and [src/main/resources/application-prod.properties](/workspaces/workout-tracker-api/src/main/resources/application-prod.properties) for environment-specific settings.
   - External references:
     - Spring Boot testing docs for WebMvcTest, DataJpaTest, and integration test patterns.
     - JUnit 5 and Mockito docs for service and controller tests.
     - springdoc-openapi docs if you want Swagger UI and OpenAPI generation.
     - Flyway or Liquibase docs if you move from ddl-auto=update to migrations.
   - Suggested test targets: registration, login, validation failures, authorization failures, repository queries, and summary endpoints.

6. Prepare the repo for presentation and deployment, *depends on steps 1-5*.
   - What to implement: a README that explains the project quickly, clean config separation, and easy local setup.
   - Repo resources:
     - [README.md](/workspaces/workout-tracker-api/README.md) for the public-facing project story.
     - [src/main/resources/application.properties](/workspaces/workout-tracker-api/src/main/resources/application.properties) and [src/main/resources/application-prod.properties](/workspaces/workout-tracker-api/src/main/resources/application-prod.properties) for local vs production configuration.
     - [docker-compose.yml](/workspaces/workout-tracker-api/docker-compose.yml) for the database/runtime setup.
     - [pom.xml](/workspaces/workout-tracker-api/pom.xml) for build packaging and dependency review.
   - External references:
     - Docker and Docker Compose docs for environment setup.
     - Spring Boot externalized configuration docs for profile-based settings.
     - README best-practice examples from strong portfolio backend repos.

**Relevant files**
- [src/main/java/com/edmilson/workouttracker/controller/AuthController.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/controller/AuthController.java)
- [src/main/java/com/edmilson/workouttracker/controller/HealthController.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/controller/HealthController.java)
- [src/main/java/com/edmilson/workouttracker/config/SecurityConfig.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/config/SecurityConfig.java)
- [src/main/java/com/edmilson/workouttracker/config/PasswordConfig.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/config/PasswordConfig.java)
- [src/main/java/com/edmilson/workouttracker/service/UserService.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/service/UserService.java)
- [src/main/java/com/edmilson/workouttracker/entity/User.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/entity/User.java)
- [src/main/java/com/edmilson/workouttracker/repository/UserRepository.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/repository/UserRepository.java)
- [src/main/java/com/edmilson/workouttracker/dto/RegisterRequest.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/dto/RegisterRequest.java)
- [src/main/java/com/edmilson/workouttracker/dto/RegisterResponse.java](/workspaces/workout-tracker-api/src/main/java/com/edmilson/workouttracker/dto/RegisterResponse.java)
- [src/main/resources/application.properties](/workspaces/workout-tracker-api/src/main/resources/application.properties)
- [src/main/resources/application-prod.properties](/workspaces/workout-tracker-api/src/main/resources/application-prod.properties)
- [src/test/java/com/edmilson/workouttracker/WorkoutTrackerApplicationTests.java](/workspaces/workout-tracker-api/src/test/java/com/edmilson/workouttracker/WorkoutTrackerApplicationTests.java)
- [pom.xml](/workspaces/workout-tracker-api/pom.xml)
- [README.md](/workspaces/workout-tracker-api/README.md)
- [docker-compose.yml](/workspaces/workout-tracker-api/docker-compose.yml)

**Verification**
1. Add focused tests after each phase: validation, service behavior, controller behavior, repository queries, and auth/security rules.
2. Run the Maven test suite after each major slice and verify the build still passes cleanly.
3. Start the app against PostgreSQL locally and confirm the production profile also boots with the intended settings.
4. Exercise the key endpoints manually or with HTTP client requests to confirm auth, protected routes, and error responses behave as documented.
5. Check the README and API docs from a fresh perspective to make sure the project can be understood quickly.

**Decisions**
- This plan is backend-first and assumes the portfolio value comes from the API itself.
- The current auth implementation should be treated as a starting point, not a final design.
- Prefer a few polished features over many shallow endpoints.
- Exclude unrelated refactors and frontend work unless you later decide to add a companion client.

**Further Considerations**
1. If you want the strongest portfolio signal, pair this backend with OpenAPI docs and a small demo client later.
2. If you want faster delivery, trim the plan to auth, workout CRUD, progress summaries, tests, and docs first.
3. If you want a more senior-looking backend, the next upgrade after this plan would be JWT auth plus migrations and integration tests.
