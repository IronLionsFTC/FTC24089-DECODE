import pygame
import math
import sys
import time

# --- Setup ---
pygame.init()

SCALE = 5  # pixels per inch
WIDTH, HEIGHT = 120 * SCALE, 120 * SCALE
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Projectile Motion (Hollow 5-inch Ball)")

# --- Colors ---
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
RED = (255, 0, 0)

# --- Physics constants ---
g = 385.0  # in/s² (gravity)
BALL_DIAM = 5.0  # inches
BALL_RADIUS_PX = int(BALL_DIAM / 2 * SCALE)

# --- Air properties ---
rho = 0.00000237  # slugs/in³ (air density)

# --- Ball properties ---
m = 0.00512  # 75 g = 0.00512 slugs
Cd = 0.3  # reduced due to holes (less drag)
A = 0.7 * math.pi * (BALL_DIAM / 2) ** 2  # effective area reduced by 30%

dt = 0.01  # timestep (seconds)

d = 120
h = 0

# --- User input ---
v0 = 210
angle_rad = 1 / math.atan((-d + math.sqrt(d**2 - 768 * (d) / ())) / ())

# Velocity components
vx = v0 * math.sin(angle_rad)
vy = -v0 * math.cos(angle_rad)  # negative because y increases downward in pygame

# --- Initial position ---
x, y = BALL_RADIUS_PX, HEIGHT - BALL_RADIUS_PX
trajectory = []

# --- Timing ---
clock = pygame.time.Clock()
start_time = time.time()

# --- Main simulation loop ---
running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()

    # --- Calculate velocity magnitude and drag ---
    v = math.sqrt(vx**2 + vy**2)
    if v > 0:
        Fd = 0.5 * Cd * rho * A * v**2
        ax = -(Fd / m) * (vx / v)
        ay = g - (Fd / m) * (vy / v)
    else:
        ax, ay = 0, g

    # --- Integrate motion ---
    vx += ax * dt
    vy += ay * dt
    x += vx * dt * SCALE
    y += vy * dt * SCALE

    # --- Stop when the ball hits ground or leaves window ---
    if y >= HEIGHT - BALL_RADIUS_PX or x >= WIDTH or y < 0:
        break

    trajectory.append((int(x), int(y)))

    # --- Draw ---
    screen.fill(WHITE)
    for px, py in trajectory:
        pygame.draw.circle(screen, BLACK, (px, py), 1)
    pygame.draw.circle(screen, RED, (int(x), int(y)), BALL_RADIUS_PX)

    # --- Optional: show info ---
    elapsed = time.time() - start_time
    font = pygame.font.SysFont(None, 24)
    text = font.render(f"t={elapsed:.2f}s  v={v:.1f} in/s  height={HEIGHT - y:.1f} in", True, BLACK)
    screen.blit(text, (10, 10))

    pygame.display.flip()
    clock.tick(int(1 / dt * 0.1))  # real-time pacing

pygame.quit()
