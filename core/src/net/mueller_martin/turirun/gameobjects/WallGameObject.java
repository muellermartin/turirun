package net.mueller_martin.turirun.gameobjects;


import net.mueller_martin.turirun.utils.CollusionDirections;

/**
 * Created by Dorothea on 07.11.2015.
 */
public class WallGameObject extends GameObject{

    public WallGameObject (float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public void update(float deltaTime)
    {

    }

    public void isCollusion(GameObject otherObject, CollusionDirections.CollusionDirectionsTypes type) {

    }

}
