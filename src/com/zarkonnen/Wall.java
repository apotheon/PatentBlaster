package com.zarkonnen;

import com.zarkonnen.catengine.Draw;
import com.zarkonnen.catengine.util.Clr;

public class Wall extends Entity {
	public static final Clr WALL_C = new Clr(85, 85, 85);
	
	public Wall(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tint = WALL_C;
		gravityMult = 0;
		collides = false;
		ignoresWalls = true;
	}
	
	public boolean isCollidedWith = true;
	public boolean destructible;
	public double initialHP;
	public double hp;
	public boolean isFloor = false;
	public int imgDelta;
	
	public Wall tint(Clr tint) { this.tint = tint; return this; }
	
	public void drawGlow(Draw d, Level l, double scrollX, double scrollY) {}
	
	public Wall floor() { isFloor = true; return this; }
	
	public boolean isMainWall = false;
	
	public Wall mainWall() { isMainWall = true; return this; }
	
	public void takeDamage(Level l, Shot s) {
		if (!destructible) { return; }
		if (s.weapon == null) { return; }
		if (hp <= 0) { return; }
		hp -= s.weapon.dmg * s.dmgMultiplier;
	}
	
	@Override
	public void draw(Draw d, Level l, double scrollX, double scrollY) {
		if (img != null) {
			d.blit(img, x + scrollX, y + scrollY + imgDelta);
		} else {
			d.rect(tint, x + scrollX, y + scrollY, w, h);
		}
	}
	
	public void smash(Level l) {
		for (int gy = 0; gy < h; gy += 10) {
			for (int gx = 0; gx < w; gx += 10) {
				Shot s = new Shot(tint, 10, 10, false, 60 + l.r.nextInt(60), x + gx, y + gy, l.r.nextDouble() - 0.5, l.r.nextDouble() - 0.5, 1.0, null, false, false, false, x + gx, y + gy, 0);
				l.shotsToAdd.add(s);
			}
		}
	}
}
