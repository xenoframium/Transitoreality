#include "Entity.hpp"
#include "Graphics.hpp"
#include "TexturedRenderable.hpp"
#include "EntityAssembler.hpp"

const int WINDOW_WIDTH = 640;
const int WINDOW_HEIGHT = 480;
const std::string WINDOW_TITLE = "Test";
const float WINDOW_ASPECT_RATIO = ((float) WINDOW_HEIGHT) / WINDOW_WIDTH;

using namespace transitoreality;

struct t {
    int v;
    t(int v) : v(v) {}
};

int main(int argc, const char * argv[]) {

    if (!initGL()) {
        printf("Could not initialise GLFW3.");
        return 1;
    }

    GLFWwindow *window = createWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE);
    if (!window) {
        printf("Failed to create window.");
        return 1;
    }
    if (!initGLEW()) {
        printf("Failed to initialise GLEW.");
        return 1;
    }
    glDepthFunc(GL_LEQUAL);

    std::vector<float> points = {
        0.5, 0.5, 0,
        0.5, -0.5, 0,
        -0.5, -0.5, 0
    };

    std::vector<float> points2 = {
        0.5, 0.5, 0,
        -0.5, 0.5, 0,
        -0.5, -0.5, 0
    };
    
    std::vector<float> uvs = {
        0, 1,
        0, 0,
        1, 0
    };
    
    std::vector<float> uvs2 = {
        0, 1,
        0, 0,
        1, 0
    };

    std::string a = "/Inputs/output.png";
    auto texture = Texture::create("/Inputs/output.png");
    auto texture2 = Texture::create("/Inputs/joke.jpg");
    
    
    EntityManager em;
    auto camera = Camera::createIsometricCamera(10.f);
    //gfx::Camera camera(glm::vec3(0, 0, 0), glm::vec3(0, 0, 1));
    Projection projection(WINDOW_ASPECT_RATIO, -10.f, 10.f);



    TransformSystem mms;
    TexturedRenderSystem rs(camera, projection);
    em.subscribeSystem(&mms, SYSTEM_PRIORITY_HIGH);
    em.subscribeSystem(&rs, SYSTEM_PRIORITY_LOW);

    Entity world = em.createEntity();
    world.addComponent<TransformComponent>();
    world.getComponent<TransformComponent>()->scale = glm::vec3(0.5, 0.5, 0.5);
    //world.getComponent<gfx::TransformComponent>()->pos = glm::vec3(-1, 0, -1);


    EntityAssembler entityAssembler(em);
    entityAssembler.registerComponentAssembler("TexturedRenderable", &TexturedRenderable::assemble);
    entityAssembler.registerComponentAssembler("TransformComponent", &TransformComponent::assemble);

    Entity entity = entityAssembler.assembleEntity("config/entities/TurquoiseFloorTile.entity");

    /*Entity entity2 = em.createEntity();
    entity2.addComponent<TransformComponent>();
    entity2.addComponent<TexturedRenderable>(TexturedRenderable::createRenderable(points2, uvs2, texture2));
    entity2.getComponent<TransformComponent>()->parent = &world;*/


    glm::vec3 axis(0, 1, 0);
    glClearColor(1, 1, 1, 1);
    float ai = 45;
    while(!glfwWindowShouldClose(window)) {
        world.getComponent<TransformComponent>()->pos += glm::vec3(0.005, 0.005, -0.005);
        //printf("%f\n", world.getComponent<TransformComponent>()->pos.z);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        em.updateSystems();
        
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    glfwTerminate();
    
    return 0;
}
