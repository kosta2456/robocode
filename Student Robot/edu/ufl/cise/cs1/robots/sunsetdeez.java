package edu.ufl.cise.cs1.robots;
import robocode.TeamRobot;
import robocode.*;
import java.awt.*;

public class sunsetdeez extends TeamRobot{

    public void run(){

        setAllColors(Color.PINK);

        boolean movement = true;
        ///This is the movement is inspired by the sample bot named Crazy
        //The movement of this bot is based on setting the ahead distance a large number and
        //following a set path that involves turning right, around twice and then right again
        //While moving this bot has that movement and also scans infinity until it finds a target.
        while(movement = true){
            turnRadarRight(Double.POSITIVE_INFINITY);
            setAhead(500000000);
                setTurnRight(90);
                waitFor(new TurnCompleteCondition(this));
                setTurnLeft(180);
                waitFor(new TurnCompleteCondition(this));
                setTurnRight(180);
                waitFor(new TurnCompleteCondition(this));
                setTurnRight(90);
            turnRadarRight(Double.POSITIVE_INFINITY);
            }

            }

    public void onScannedRobot(ScannedRobotEvent event) {
//checks to see if scanned target is a teammate
        if (isTeammate(event.getName()) == true) {
            return;
        } else {
            //This is the infinity lock
            //After scanning a target that is not a teammate the radar and gun will lock on to that
            //target and fire with a power based in distance
            setTurnRight(event.getBearing() + 90);
            setTurnRadarRightRadians(getHeadingRadians() + event.getBearingRadians() - getRadarHeadingRadians());
            setTurnGunRight(getHeading() - getGunHeading() + event.getBearing());
            if (event.getDistance() < 200) {
                fire(6);
            } else if (event.getDistance() < 800) {
                fire(5);
            } else {
                fire(4);
            }
        }
    }
//if the bot hits the wall it will get the current direction it was going and adjust to
//move away from the wall
    @Override
    public void onHitWall(HitWallEvent event) {
        super.onHitWall(event);
        if (getHeading() != event.getBearing()) {
            setTurnLeft(80);
            setAhead(250);
            setTurnRight(124);
        }
        else {
            setTurnLeft(100);
            setBack(250);
            setTurnRight(56);
        }

    }
//If the bot gets hit by a bullet then shift directions to try and avoid the lock on scanner
    @Override
    public void onBulletHit(BulletHitEvent event) {
        super.onBulletHit(event);
        setTurnLeft(80);
        setAhead(250);
        setTurnRight(124);

    }

    //If the bot hits another bot get the bearing of the robot and move away from it
    @java.lang.Override
    public void onHitRobot(HitRobotEvent event) {
        if (getHeading() != event.getBearing()) {
            setTurnLeft(80);
            setAhead(250);
            setTurnRight(124);
        }
        else {
            setTurnLeft(100);
            setBack(250);
            setTurnRight(56);
        }
    }




}
